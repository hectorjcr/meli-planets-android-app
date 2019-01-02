package com.example.hector.planets.pojos;

import java.util.Collections;
import java.util.List;

public class GraphPoints {

    private int color;

    private List<Point> points;

    public GraphPoints(List<Point> points, int color) {
        this.color = color;
        this.points = points;
        Collections.sort(points);
    }

    public List<Point> getPoints(){
        return this.points;
    }
    public int getColor(){
        return this.color;
    }
}
