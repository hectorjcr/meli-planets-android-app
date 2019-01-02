package com.example.hector.planets.pojos;

import com.example.hector.planets.services.Function;

public class GraphFunction {
    private int color;
    private Function f;

    public GraphFunction(Function f, int color) {
        this.color = color;
        this.f = f;
    }

    public int getColor() {
        return color;
    }

    public Function getFunction() {
        return f;
    }
}
