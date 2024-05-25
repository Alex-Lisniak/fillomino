package com.example.programming_engineering_lab2.dto;

public class IntegerPair {
    private Integer x;
    private Integer y;
    public IntegerPair (Integer x, Integer y) {
        this.x = x.intValue();
        this.y = y.intValue();
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "IntegerPair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}