package com.bounce.BallBounce.entity;

import java.util.List;

public class PostCharactersList {
    private List<Pair> result;
    private double max;
    private double min;
    private double aver;

    public PostCharactersList(List<Pair> list, double max, double min, double aver) {
        this.result = list;
        this.max = max;
        this.min = min;
        this.aver = aver;
    }

    public List<Pair> getList() {
        return result;
    }

    public void setList(List<Pair> list) {
        this.result = list;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getAver() {
        return aver;
    }

    public void setAver(double aver) {
        this.aver = aver;
    }
}
