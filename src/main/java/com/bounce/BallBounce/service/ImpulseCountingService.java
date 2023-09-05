package com.bounce.BallBounce.service;

import com.bounce.BallBounce.entity.BallParameters;
import com.bounce.BallBounce.entity.Pair;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ImpulseCountingService {
    private static final Logger Logger = LoggerFactory.getLogger(ImpulseCountingService.class);
    public BallParameters getSummaryImpulse(double velocity1, double velocity2, double mass1, double mass2) {
        Logger.info("Service is processing parameters");
        if (velocity1 == 1 && velocity2 == 1 && mass1 == 1 && mass2 == 1) {
            throw new ArithmeticException("Wrong Parameters");
        }
        return countSummaryImpulse(velocity1, velocity2, mass1, mass2);
    }

    public double getMaxVelocity(List<Pair> paramList){
        return paramList.stream().filter(pair -> pair.getParams() != null).mapToDouble(pair -> pair.getParams().getFinalVelocity()).max().getAsDouble();

    }

    public double getMinVelocity(List<Pair> paramList){
        return paramList.stream().filter(pair -> pair.getParams() != null).mapToDouble(pair -> pair.getParams().getFinalVelocity()).min().getAsDouble();
    }

    public double getAverageVelocity(List<Pair> paramList){
        return paramList.stream().filter(pair -> pair.getParams() != null).mapToDouble(pair -> pair.getParams().getFinalVelocity()).average().getAsDouble();
    }


    private BallParameters countSummaryImpulse(double velocity1, double velocity2, double mass1, double mass2) {
        return new BallParameters((velocity1 * mass1 + velocity2 * mass2)/(mass1 + mass2), velocity1, velocity2, mass1, mass2);
    }

}
