package com.example.hector.planets.pojos;

public class Label {

    private String label;

    private double tick;

    public Label(String label, double tick) {
        this.label = label;
        this.tick = tick;
    }

    public String getLabel() {
        return label;
    }

    public double getTick() {
        return tick;
    }
}
