package com.example.hector.planets.models;

import android.graphics.Color;

import com.example.hector.planets.Graph;
import com.example.hector.planets.pojos.Planet;
import com.example.hector.planets.pojos.Point;

import java.util.ArrayList;
import java.util.List;

public class GraphModels {

    List<Planet> planets;



    public GraphModels(List<Planet> planets) {
        this.planets = planets;
    }

    public List<Planet> getPlanets() {
        return planets;
    }
    public void updatePlanetsTime(int t){
        for (Planet p:this.planets){
            p.setT(t);
        }
    }
    public Graph builGraph(int t){
        updatePlanetsTime(t);
        List<Point> points = new ArrayList<Point>();
        for (Planet p: planets){
            points.add(new Point(p.getX(),p.getY()));
        }
        Graph graph = new Graph.Builder()
                .setWorldCoordinates(-2500,2500,-2500,2500)
                .addFunction(x -> Math.sqrt(Math.pow(500,2)-Math.pow(x,2)),Color.YELLOW)
                .addFunction(x -> - Math.sqrt(Math.pow(500,2)-Math.pow(x,2)),Color.YELLOW)
                .addFunction(x -> Math.sqrt(Math.pow(2000,2)-Math.pow(x,2)),Color.BLUE)
                .addFunction(x -> - Math.sqrt(Math.pow(2000,2)-Math.pow(x,2)),Color.BLUE)
                .addFunction(x -> Math.sqrt(Math.pow(1000,2)-Math.pow(x,2)),Color.RED)
                .addFunction(x -> - Math.sqrt(Math.pow(1000,2)-Math.pow(x,2)),Color.RED)
                .addPoints(points,Color.GREEN)
                //.addLineGraph(points,Color.GRAY)
                .setXTicks( new double[] {-2000, -1500, -1000, -500, 500, 1000, 2000})
                .setYTicks( new double[] {-2000, -1500, -1000, -500, 500, 1000, 2000})
                .build();
        return graph;
    }
}
