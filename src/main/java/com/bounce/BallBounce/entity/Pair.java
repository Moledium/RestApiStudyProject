package com.bounce.BallBounce.entity;

public class Pair {
    private BallParameters params;
    private ValidationImpulseError errors;

    public Pair(BallParameters param, ValidationImpulseError error){
        this.params = param;
        this.errors = error;
    }

    public BallParameters getParams() {
        return params;
    }

    public void setParams(BallParameters params) {
        this.params = params;
    }

    public ValidationImpulseError getErrors() {
        return errors;
    }

    public void setErrors(ValidationImpulseError errors) {
        this.errors = errors;
    }
}
