package com.bounce.BallBounce.entity;

import java.util.ArrayList;
import java.util.List;

public class ValidationImpulseError {
    private List<String> errorRange = new ArrayList<String>();
    private  String errorCode;

    public ValidationImpulseError() {
    }
    public void addErrorMessage(String errorMessage){
        errorRange.add(errorMessage);
    }

    public List<String> getErrorRange() {
        return errorRange;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
