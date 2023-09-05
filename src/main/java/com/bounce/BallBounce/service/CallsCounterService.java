package com.bounce.BallBounce.service;

import com.bounce.BallBounce.entity.NumberOfCalls;
import org.springframework.stereotype.Service;

@Service
public class CallsCounterService {
    public void countCalls(){
        NumberOfCalls.getInstance().increaseCounter();
    }
}
