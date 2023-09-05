package com.bounce.BallBounce.entity;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class InMemoryStorage {
    private final Map<double[], BallParameters> dataStorage = new HashMap<>();

    public void saveParams(double[] values, BallParameters params){
        dataStorage.put(values, params);
    }

    public BallParameters getVelocityByParams(double[] array){
        for (Map.Entry<double[], BallParameters> entry : dataStorage.entrySet()) {
            if (Arrays.equals(entry.getKey(), array)) return entry.getValue();
        }
        return null;
    }

    public boolean isContainsInMap(double[] array){
        AtomicBoolean isEqual = new AtomicBoolean(false);
        dataStorage.forEach((k, v)->{
            if(Arrays.equals(k, array)) isEqual.set(true);
        });
        return isEqual.get();
    }

    public int getParamsNumber(){
        return dataStorage.size();
    }
    public List<BallParameters> getAllSavedParams(){
        List<BallParameters> listParams = new ArrayList<BallParameters>();
        dataStorage.forEach((k,v)->listParams.add(v));
        return listParams;
    }
}
