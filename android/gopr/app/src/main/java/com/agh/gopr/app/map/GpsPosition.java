package com.agh.gopr.app.map;

import com.esri.core.geometry.Point;

import org.json.JSONObject;

public class GpsPosition {
    private double x;
    private double y;

    private GpsPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static GpsPosition fromPoint(Point point) {
       return new GpsPosition(point.getX(), point.getY());
    }

    public JSONObject toJSON() {
        return null;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
