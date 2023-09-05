package com.bounce.BallBounce.entity;

public class NumberOfCalls {
    private static NumberOfCalls INSTANCE = new NumberOfCalls();
    private int counter = 0;

    private NumberOfCalls(){
    }

    public static synchronized NumberOfCalls getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NumberOfCalls();
        }
        return INSTANCE;
    }

    public synchronized void increaseCounter(){
        counter++;
    }
    public int getCounter() {
        return counter;
    }
}
