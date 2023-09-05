package com.bounce.BallBounce.service;

import com.bounce.BallBounce.MySQLAccess.IdCounterRepository;
import com.bounce.BallBounce.MySQLAccess.ParametersRepository;
import com.bounce.BallBounce.MySQLAccess.template.IdCounter;
import com.bounce.BallBounce.MySQLAccess.template.ParametersEntity;
import com.bounce.BallBounce.controller.ImpulseCountingController;
import com.bounce.BallBounce.entity.BallParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DataBaseService {
    @Autowired
    private ParametersRepository mainRepository;
    @Autowired
    private IdCounterRepository idRepository;
    private static final Logger Logger = LoggerFactory.getLogger(ImpulseCountingController.class);


    public void saveParams(BallParameters params){
        ParametersEntity entity = new ParametersEntity(params.getVelocity1(), params.getVelocity2(),
                params.getMass1(), params.getMass2(), params.getFinalVelocity());
        List<IdCounter> idList = idRepository.findAll();
        if(idList.size() != 0) {
            IdCounter maxId = Collections.max(idList, Comparator.comparing(IdCounter::getId));
            entity.setFutureId(maxId.getId());
        }
        try {
            entity = mainRepository.saveAndFlush(entity);
            mainRepository.save(entity);
        }catch(DataAccessException exc){
            Logger.error(exc.getLocalizedMessage());
        }
    }

    public ParametersEntity findByFutureId(Long id){
        return mainRepository.findByFutureId(id);
    }

    public void saveFutureId(IdCounter id) {
        try {
            id = idRepository.saveAndFlush(id);
            idRepository.save(id);
        } catch (DataAccessException exc) {
            Logger.error(exc.getLocalizedMessage());
        }
    }

    public synchronized IdCounter syncId(){
        IdCounter id = new IdCounter();
        saveFutureId(id);
        return id;
    }

    public List<ParametersEntity> getAllSavedParams(){
        return mainRepository.findAll();
    }

    public BallParameters isContainsInDataBase(BallParameters params){
        ParametersEntity temp = mainRepository.findByVelocity1AndVelocity2AndMass1AndMass2(params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2());
        if(temp == null) return null;
        else{
            return new BallParameters(temp.getVelocity1(), temp.getVelocity2(), temp.getMass1(), temp.getMass2(), temp.getFinalVelocity());
        }
    }

}
