package com.example.hector.planets.pojos;

import android.support.annotation.NonNull;

public class Point implements Comparable<Point> {

    private double x;

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(@NonNull Point o) {
        if(this.x < o.x){
            return -1;
        }
        if(this.x == o.x){
            return 0;
        }
        return 1;
    }
}
