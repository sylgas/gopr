package com.agh.gopr.app.map;

import com.esri.core.geometry.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public static JSONObject createJSON(String name, List<GpsPosition> positionList) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", name);
        JSONArray positions = new JSONArray();
        for (GpsPosition position : positionList) {
            positions.put(position.toJSON());
        }
        json.put("positions", positions);
        return json;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("x", x);
        object.put("y", y);
        return object;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
