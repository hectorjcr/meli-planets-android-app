package com.example.hector.planets.pojos;

import android.content.res.Resources;
import android.support.annotation.ColorInt;

public class Planet {
    private String name; //nombre del planeta
    private float r; //radio
    private float w; //velocidad angular
    private float x=0; //posicion en x
    private float y=0; //posicion en y
    private int t; //tiempo
    private int orbitColor; //color de la linea de orbita
    private int planetColor; //color del planete
    private float offsetAngle = 0; //angulo de desplazamiento
    private float radians = 0; //conversion de angulo de desplazamiento a radianes
    private float planetSize=10;

    public Planet(String name, float r, float w, int t, @ColorInt int orbitColor,@ColorInt int planetColor) {
        this.name = name;
        this.r = r;
        this.w = w;
        this.t = t;
        this.orbitColor = orbitColor;
        this.planetColor = planetColor;
        this.calcs(t);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
        calcs(getT());
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
        calcs(getT());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
        calcs(t);
    }

    public int getOrbitColor() {
        return orbitColor;
    }

    public void setOrbitColor(int orbitColor) {
        this.orbitColor = orbitColor;
    }

    public int getPlanetColor() {
        return planetColor;
    }

    public void setPlanetColor(int planetColor) {
        this.planetColor = planetColor;
    }

    public float getOffsetAngle() {
        return offsetAngle;
    }

    public float getRadians() {
        return radians;
    }

    public float getPlanetSize() {
        return planetSize;
    }

    public void setPlanetSize(float planetSize) {
        this.planetSize = planetSize;
    }

    /*
        Metodos de calculo
         */
    private void calcs(int t){
        calcOffsetAngle(t);
        calculateRadians(t);
        calcXPosition();
        calcYPosition();
    }
    private void calcXPosition(){
        this.x = this.r * (float)Math.cos(this.radians);
    }
    private void calcYPosition(){
        this.y = this.r * (float) Math.sin(this.radians);
    }

    private void calcOffsetAngle(int t){
        this.offsetAngle = this.w * t;
    }

    private void calculateRadians(int t){
        this.radians = (float) Math.toRadians((double) this.offsetAngle);
    }
    public float kmsToPointsRadius(){
        return this.r / 10;
    }
    public float kmsToPointsX(){
        return this.x / 10;
    }
    public float kmsToPointsY(){
        return this.y / 10;
    }



    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", r=" + r +
                ", w=" + w +
                ", x=" + x +
                ", y=" + y +
                ", t=" + t +
                '}';
    }

}
