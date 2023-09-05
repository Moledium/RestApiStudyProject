package com.bounce.BallBounce.validator;

import com.bounce.BallBounce.entity.ValidationImpulseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class VelocityValidator {
    private static final Logger Logger = LoggerFactory.getLogger(VelocityValidator.class);
    public ValidationImpulseError validateParameters(double velocity1, double velocity2, double mass1, double mass2){
        ValidationImpulseError response = new ValidationImpulseError();
        if(mass1 == 0 || mass2 == 0){
            Logger.info("Mass can't be zero");
            response.addErrorMessage("Mass can't be zero");
        }
        if(velocity1 < 0){
            Logger.info("Velocity of the first object can't be negative");
            response.addErrorMessage("Velocity of the first object can't be negative");
        }
        if(velocity1 == 0 && velocity2 == 0){
            Logger.info("Both objects aren't moving");
            response.addErrorMessage("Both objects aren't moving");
        }
        if(velocity1 >= Double.MAX_VALUE || velocity2 >= Double.MAX_VALUE
                || mass1 >= Double.MAX_VALUE || mass2 >= Double.MAX_VALUE){
            Logger.info("Too big numeric value");
            response.addErrorMessage("Too big numeric value");
        }
        return response;
    }
}
