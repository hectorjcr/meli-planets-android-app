package com.example.hector.planets.Utilities;

import com.example.hector.planets.pojos.Planet;

import java.util.List;

public class CalcsUtils {
    //int s = (y2 - y1) * x + (x1 - x2) * y + (x2 * y1 - x1 * y2);
    public static boolean isCollinear(List<Planet> planets){
        Planet p1 = planets.get(0),p2 = planets.get(1),p3=planets.get(2);
        float s = ((p2.getY()-p1.getY())*p3.getX()) + ((p1.getX()-p2.getX())*p3.getY()) + ((p2.getX()*p1.getY())-(p1.getX()*p2.getY()));
        if(s == 0)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
    public static boolean isSunCollinear(List<Planet>planets){
        Planet p1 = planets.get(0),p2 = planets.get(1),p3=planets.get(2);
        float s = ((p2.getX()*p1.getY())-(p1.getX()*p2.getY()));
        if(s==0)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public static float perimeter(List<Planet> planets) {
        return pointsDistance(planets.get(0),planets.get(1)) +
               pointsDistance(planets.get(1),planets.get(2)) +
               pointsDistance(planets.get(2),planets.get(0));
    }

    public static float pointsDistance(Planet a, Planet b){
        return (float) Math.hypot((b.getY()-a.getY()), (b.getX()-a.getX()));
    }
}
