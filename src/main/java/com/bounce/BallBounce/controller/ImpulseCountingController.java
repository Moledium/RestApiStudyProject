package com.bounce.BallBounce.controller;

import com.bounce.BallBounce.MySQLAccess.template.IdCounter;
import com.bounce.BallBounce.entity.*;
import com.bounce.BallBounce.service.CallsCounterService;
import com.bounce.BallBounce.service.DataBaseService;
import com.bounce.BallBounce.service.ImpulseCountingService;
import com.bounce.BallBounce.validator.VelocityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/api/version1")
public class ImpulseCountingController {
    private static final Logger Logger = LoggerFactory.getLogger(ImpulseCountingController.class);
    private final ImpulseCountingService impulseService;
    private final VelocityValidator validator;
    private final InMemoryStorage inMemoryStorage;
    private final CallsCounterService callsService;
    private final DataBaseService dataBaseService;
    @Autowired
    public ImpulseCountingController(ImpulseCountingService impulseService, VelocityValidator validator, InMemoryStorage storage, CallsCounterService calls, DataBaseService dataBaseService){
        this.impulseService = impulseService;
        this.validator = validator;
        this.inMemoryStorage = storage;
        this.callsService = calls;
        this.dataBaseService = dataBaseService;
    }


    @GetMapping(path = "/impulses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getImpulse(@RequestParam double velocity1, @RequestParam double mass1,
                                              @RequestParam double velocity2, @RequestParam double mass2){
        callsService.countCalls();
        ValidationImpulseError response = validator.validateParameters(velocity1, velocity2, mass1, mass2);
        if(response.getErrorRange().size() != 0){
            response.setErrorCode(HttpStatus.BAD_REQUEST.name());
            Logger.error("Some of Object Parameters are Not Valid");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            double[] paramArray = {velocity1, velocity2, mass1, mass2};
            if(inMemoryStorage.isContainsInMap(paramArray)){
                return ResponseEntity.ok(inMemoryStorage.getVelocityByParams(paramArray));
            }
            BallParameters temp;
            if((temp = dataBaseService.isContainsInDataBase(new BallParameters(0, velocity1, velocity2, mass1, mass2))) != null){
                return ResponseEntity.ok(temp);
            }
            BallParameters countedParams = impulseService.getSummaryImpulse(velocity1, velocity2, mass1, mass2);
                if(countedParams.getFinalVelocity() == 0){
                Logger.error("Velocity for such parameters is Not Found");
                response.addErrorMessage("Velocity for such parameters is Not Found");
                response.setErrorCode(HttpStatus.NOT_FOUND.name());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            inMemoryStorage.saveParams(paramArray, countedParams);
            dataBaseService.saveParams(countedParams);
            Logger.info("Object saved. No exceptions occurred");
            return ResponseEntity.ok(countedParams);
        } catch(Exception except){
            Logger.error("Internal Server Error occurred");
            response.addErrorMessage("Internal Server Error occurred");
            response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/impulses/cash")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllCashParams(){
        Logger.info("All cash params got");
        return ResponseEntity.ok(inMemoryStorage.getAllSavedParams());
    }
    @GetMapping(path = "/impulses/cash/size")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllCashParamsNumber(){
        Logger.info("Number of current params got");
        return ResponseEntity.ok(inMemoryStorage.getParamsNumber());
    }
    @GetMapping(path = "/calls")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getNumberOfCalls(){
        Logger.info("Number of calls got");
        return ResponseEntity.ok(NumberOfCalls.getInstance().getCounter());
    }

    @GetMapping(path = "impulses/dataBase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllDataBaseParams(){
        Logger.info("All DataBase params got");
        return ResponseEntity.ok(dataBaseService.getAllSavedParams());
    }

    @PostMapping(path = "/params")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostCharactersList> postVelocityParams(@RequestBody List<BallParameters> paramList){
        AtomicInteger exceptCounter = new AtomicInteger();
        List<Pair> result = new ArrayList<>();
        paramList.forEach(params -> {
            BallParameters countedParams = null;
            ValidationImpulseError exceptList = validator.validateParameters(params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2());
            if(exceptList.getErrorRange().size() != 0){
                exceptList.setErrorCode(HttpStatus.BAD_REQUEST.name());
                result.add(new Pair(null, exceptList));
                return;
            }
            try{
                double[] paramArray = {params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2()};
                if(inMemoryStorage.isContainsInMap(paramArray)){
                    result.add(new Pair( inMemoryStorage.getVelocityByParams(paramArray), null));
                    return;
                }
                if(dataBaseService.isContainsInDataBase(new BallParameters(0, params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2())) != null){
                    result.add(new Pair(dataBaseService.isContainsInDataBase(new BallParameters(0, params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2())), null));
                    return;
                }
                countedParams = impulseService.getSummaryImpulse(params.getVelocity1(), params.getVelocity2(), params.getMass1(), params.getMass2());
                if(countedParams.getFinalVelocity() == 0){
                    Logger.error("Velocity for such parameters is Not Found");
                    exceptList.addErrorMessage("Velocity for such parameters is Not Found");
                    exceptList.setErrorCode(HttpStatus.NOT_FOUND.name());
                    result.add(new Pair(null, exceptList));
                    return;
                }
                inMemoryStorage.saveParams(paramArray, countedParams);
                dataBaseService.saveParams(countedParams);
            } catch(Exception except){
                Logger.error("Internal Server Error occurred");
                exceptList.addErrorMessage("Internal Server Error occurred");
                exceptList.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
            }
                result.add(new Pair(countedParams, exceptList));
        });
        result.forEach(member ->{
            if(member.getErrors() != null && member.getErrors().getErrorRange().size() != 0) exceptCounter.getAndIncrement();
            else member.setErrors(null);
        });
            if(exceptCounter.get() == result.size())
                return new ResponseEntity<>(new PostCharactersList(result,impulseService.getMaxVelocity(result),
                        impulseService.getMinVelocity(result),impulseService.getAverageVelocity(result) ), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(new PostCharactersList(result,impulseService.getMaxVelocity(result),
                        impulseService.getMinVelocity(result),impulseService.getAverageVelocity(result) ), HttpStatus.CREATED);
    }
    @GetMapping(path = "/async")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAsyncId(@RequestParam double velocity1, @RequestParam double mass1,
                                  @RequestParam double velocity2, @RequestParam double mass2 ){
        Logger.info("Async call started");
        if(validator.validateParameters(velocity1, velocity2, mass1, mass2).getErrorRange().size() != 0){
            Logger.error("Some of Object Parameters are Not Valid");
            return new ResponseEntity<>(new AsyncCallId("Validation incorrect. Check input parameters", null), HttpStatus.BAD_REQUEST);
        }
/*        IdCounter id = new IdCounter();
        dataBaseService.saveFutureId(id);*/
        IdCounter id = dataBaseService.syncId();
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                Logger.info("Started processing params");
                BallParameters countedParams = impulseService.getSummaryImpulse(velocity1, velocity2, mass1, mass2);
                dataBaseService.saveParams(countedParams);
                Logger.info("Object saved");
            }
        });
        Logger.info("Return response");
        return new ResponseEntity<>(new AsyncCallId("Validation correct. Triangle will be added to database soon.", id.getId()), HttpStatus.OK);
    }

    @GetMapping(path = "/async/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getParamsById(Long id){
        return new ResponseEntity<>(dataBaseService.findByFutureId(id), HttpStatus.OK);
    }
}

