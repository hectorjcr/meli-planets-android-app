package com.example.hector.planets.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hector.planets.Utilities.CalcsUtils;
import com.example.hector.planets.pojos.Planet;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {

    private float cx = this.getWidth()/2;
    private float cy = this.getHeight()/2;
    private float cxToDp;
    private float cyToDp;
    private Resources resources;
    Paint paint;
    List<Planet> planets = new ArrayList<Planet>();

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.cx = this.getWidth()/2;
        this.cy = this.getHeight()/2;
        this.resources = getResources();
        cxToDp = convertToDp(this.getWidth()/2);
        cyToDp = convertToDp(this.getHeight()/2);
    }

    public void setPlanets(List<Planet>planets){
        this.planets = planets;
    }

    public void updatePlanetsTime(int t){
        for (Planet planet:planets) {
            planet.setT(t);
        }
        postInvalidate();
    }

    private void paintElements(Canvas canvas, List<Planet>planets){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(cx,cy,10,paint);
        for (Planet planet:planets) {
            paintElement(canvas,planet);
        }
        if(CalcsUtils.isCollinear(planets) && !CalcsUtils.isSunCollinear(planets)){
            paintLineBetweenPlanets(canvas,planets);
        }
        else if(CalcsUtils.isSunCollinear(planets) && CalcsUtils.isSunCollinear(planets)){
            paintLineSunPlanets(canvas,planets);
        }
        else{
            painTriangle(canvas,planets);
        }
        paintLineBetweenPlanets(canvas,planets);

    }

    private void paintElement(Canvas canvas, Planet planet){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(planet.getOrbitColor());
        canvas.drawCircle(cx,cy,planet.kmsToPointsRadius(),paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(planet.getPlanetColor());
        canvas.drawCircle(cx+planet.kmsToPointsX(),cy-planet.kmsToPointsY(),planet.getPlanetSize(),paint);
        //canvas.drawCircle(cxToDp+convertToDp(planet.getX()),cyToDp+convertToDp(planet.getY()),planet.getPlanetSize(),paint);
    }

    private void paintLineBetweenPlanets(Canvas canvas, List<Planet> planets){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#0D4D4D"));
        canvas.drawLine(cx+planets.get(0).kmsToPointsX(),cy+planets.get(0).kmsToPointsY(),cx+planets.get(1).kmsToPointsX(),cy+planets.get(1).kmsToPointsY(),paint);
        canvas.drawLine(cx+planets.get(1).kmsToPointsX(),cy+planets.get(1).kmsToPointsY(),cx+planets.get(2).kmsToPointsX(),cy+planets.get(2).kmsToPointsY(),paint);
        canvas.drawLine(cx+planets.get(2).kmsToPointsX(),cy+planets.get(2).kmsToPointsY(),cx+planets.get(0).kmsToPointsX(),cy+planets.get(0).kmsToPointsY(),paint);
    }

    private void paintLineSunPlanets(Canvas canvas, List<Planet>planets){
        //TODO pintar linea entre sol y planetas
    }

    private void painTriangle(Canvas canvas, List<Planet> planets){
        //TODO pintar triangulo entre planetas
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.cx = canvas.getWidth()/2;
        this.cy = canvas.getHeight()/2;
        cxToDp = convertToDp(canvas.getWidth()/2);
        cyToDp = convertToDp(canvas.getHeight()/2);
        paintElements(canvas,planets);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(cx,cy,10,paint);
    }

    private float convertToDp( float pixels){
        return pixels * getDensity();
    }
    private float getDensity(){
        return resources.getDisplayMetrics().density;
    }
}
