package com.example.hector.planets;

import com.example.hector.planets.pojos.GraphFunction;
import com.example.hector.planets.pojos.GraphPoints;
import com.example.hector.planets.pojos.Label;
import com.example.hector.planets.pojos.Point;
import com.example.hector.planets.services.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    private final int axesColor;
    private final double axisX;
    private final double axisY;
    private final int bgColor;
    private final List<GraphFunction> functions;
    private final List<GraphPoints> graphPoints;
    private final List<GraphPoints> lineGraphs;
    private final List<Label> xLabels;
    private final double xMax;
    private final double xMin;
    private final List<Double> xTicks;
    private final List<Label> yLabels;
    private final double yMax;
    private final double yMin;
    private final List<Double> yTicks;

    public static class Builder{

        private int axesColor = -16777216;
        private double axisX = 0.0d;
        private double axisY = 0.0d;
        private int bgColor = -1;
        Double[] defaultTicks = new Double[]{Double.valueOf(-8.0d), Double.valueOf(-6.0d), Double.valueOf(-4.0d), Double.valueOf(-2.0d), Double.valueOf(2.0d), Double.valueOf(4.0d), Double.valueOf(6.0d), Double.valueOf(8.0d)};
        private int functColor = -16777216;
        private List<GraphFunction> functions = new ArrayList(5);
        private List<GraphPoints> graphPoints = new ArrayList(5);
        private List<GraphPoints> lineGraphs = new ArrayList(5);
        private int pointColor = -16777216;
        private List<Label> xLabels = new ArrayList(10);
        private double xMax = 10.0d;
        private double xMin = -10.0d;
        private List<Double> xTicks = Arrays.asList(this.defaultTicks);
        private List<Label> yLabels = new ArrayList(10);
        private double yMax = 10.0d;
        private double yMin = -10.0d;
        private List<Double> yTicks = Arrays.asList(this.defaultTicks);

        public Builder addFunction(Function function, int graphColor) {
            this.functions.add(new GraphFunction(function, graphColor));
            return this;
        }

        public Builder addFunction(Function function) {
            this.functions.add(new GraphFunction(function, this.functColor));
            return this;
        }

        public Builder addPoints(Point[] points, int pointColor) {
            this.graphPoints.add(new GraphPoints(Arrays.asList(points), pointColor));
            return this;
        }

        public Builder addPoints(List<Point> points, int pointColor) {
            this.graphPoints.add(new GraphPoints(points, pointColor));
            return this;
        }

        public Builder addPoints(Point[] points) {
            this.graphPoints.add(new GraphPoints(Arrays.asList(points), this.pointColor));
            return this;
        }

        public Builder addPoints(List<Point> points) {
            this.graphPoints.add(new GraphPoints(points, this.pointColor));
            return this;
        }

        public Builder addLineGraph(Point[] points, int lineGraphColor) {
            this.lineGraphs.add(new GraphPoints(Arrays.asList(points), lineGraphColor));
            return this;
        }

        public Builder addLineGraph(List<Point> points, int lineGraphColor) {
            this.lineGraphs.add(new GraphPoints(points, lineGraphColor));
            return this;
        }

        public Builder addLineGraph(Point[] points) {
            this.lineGraphs.add(new GraphPoints(Arrays.asList(points), this.pointColor));
            return this;
        }

        public Builder addLineGraph(List<Point> points) {
            this.lineGraphs.add(new GraphPoints(points, this.pointColor));
            return this;
        }

        public Builder setBackgroundColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setAxesColor(int axesColor) {
            this.axesColor = axesColor;
            return this;
        }

        public Builder setFunctionColor(int functColor) {
            this.functColor = functColor;
            return this;
        }

        public Builder setPointColor(int pointColor) {
            this.pointColor = pointColor;
            return this;
        }

        public Builder setWorldCoordinates(double xMin, double xMax, double yMin, double yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            return this;
        }

        public Builder setAxes(double axisX, double axisY) {
            this.axisX = axisX;
            this.axisY = axisY;
            return this;
        }

        public Builder setXTicks(double[] xTicks) {
            List<Double> tempXTicks = new ArrayList(xTicks.length);
            for (double xTick : xTicks) {
                tempXTicks.add(Double.valueOf(xTick));
            }
            this.xTicks = tempXTicks;
            return this;
        }

        public Builder setXTicks(List<Double> xTicks) {
            this.xTicks = xTicks;
            return this;
        }

        public Builder setYTicks(double[] yTicks) {
            List<Double> tempYTicks = new ArrayList(yTicks.length);
            for (double yTick : yTicks) {
                tempYTicks.add(Double.valueOf(yTick));
            }
            this.yTicks = tempYTicks;
            return this;
        }

        public Builder setYTicks(List<Double> yTicks) {
            this.yTicks = yTicks;
            return this;
        }

        public Builder setXLabels(Label[] xLabels) {
            this.xLabels = Arrays.asList(xLabels);
            return this;
        }

        public Builder setXLabels(List<Label> xLabels) {
            this.xLabels = xLabels;
            return this;
        }

        public Builder setYLabels(Label[] yLabels) {
            this.yLabels = Arrays.asList(yLabels);
            return this;
        }

        public Builder setYLabels(List<Label> yLabels) {
            this.yLabels = yLabels;
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }

    }

    private Graph(Builder builder){
        this.functions = builder.functions;
        this.graphPoints = builder.graphPoints;
        this.lineGraphs = builder.lineGraphs;
        this.bgColor = builder.bgColor;
        this.axesColor = builder.axesColor;
        this.xMin = builder.xMin;
        this.xMax = builder.xMax;
        this.yMin = builder.yMin;
        this.yMax = builder.yMax;
        this.axisX = builder.axisX;
        this.axisY = builder.axisY;
        this.xTicks = builder.xTicks;
        this.yTicks = builder.yTicks;
        this.xLabels = builder.xLabels;
        this.yLabels = builder.yLabels;
    }

    public int getAxesColor() {
        return axesColor;
    }

    public double getAxisx() {
        return axisX;
    }

    public double getAxisY() {
        return axisY;
    }

    public int getBgColor() {
        return bgColor;
    }

    public List<GraphFunction> getFunctions() {
        return functions;
    }

    public List<GraphPoints> getGraphPoints() {
        return graphPoints;
    }

    public List<GraphPoints> getLineGraphs() {
        return lineGraphs;
    }

    public List<Label> getxLabels() {
        return xLabels;
    }

    public double getxMax() {
        return xMax;
    }

    public double getxMin() {
        return xMin;
    }

    public List<Double> getxTicks() {
        return xTicks;
    }

    public List<Label> getyLabels() {
        return yLabels;
    }

    public double getyMax() {
        return yMax;
    }

    public double getyMin() {
        return yMin;
    }

    public List<Double> getyTicks() {
        return yTicks;
    }

    public int getBackgroundColor(){
        return this.bgColor;
    }

}
