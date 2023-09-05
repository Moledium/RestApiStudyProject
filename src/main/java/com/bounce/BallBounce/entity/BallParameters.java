package com.bounce.BallBounce.entity;

public class BallParameters {
    private double finalVelocity = 0;
    private double velocity1;
    private double velocity2;
    private double mass1;
    private double mass2;

    public BallParameters(double finalVelocity, double velocity1, double velocity2, double mass1, double mass2) {
        this.finalVelocity = finalVelocity;
        this.velocity1 = velocity1;
        this.velocity2 = velocity2;
        this.mass1 = mass1;
        this.mass2 = mass2;
    }

    public BallParameters(double finalVelocity) {
        this.finalVelocity = finalVelocity;
    }

    public double getVelocity1() {
        return velocity1;
    }

    public void setVelocity1(double velocity1) {
        this.velocity1 = velocity1;
    }

    public double getVelocity2() {
        return velocity2;
    }

    public void setVelocity2(double velocity2) {
        this.velocity2 = velocity2;
    }

    public double getMass1() {
        return mass1;
    }

    public void setMass1(double mass1) {
        this.mass1 = mass1;
    }

    public double getMass2() {
        return mass2;
    }
    public void setMass2(double mass2) {
        this.mass2 = mass2;
    }

    public double getFinalVelocity() {
        return finalVelocity;
    }
    public void setFinalVelocity(double finalVelocity) {
        this.finalVelocity = finalVelocity;
    }
}
