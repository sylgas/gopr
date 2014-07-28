package com.agh.gopr.app.map;

import android.graphics.Color;

import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;

public class GpsPolyline {
    private final Polyline polyline;
    private final int id;

    public GpsPolyline(Polyline polyline, int id) {
        this.polyline = polyline;
        this.id = id;
    }

    public static Polyline buildPolyline(GpsPosition position) {
        Polyline polyline = new Polyline();
        polyline.startPath(position.toPoint());
        return polyline;
    }

    public static Graphic buildGraphicPolyline(Polyline polyline) {
        return new Graphic(polyline, buildLineSymbol());
    }

    private static Symbol buildLineSymbol() {
        return new SimpleLineSymbol(Color.RED, 4);
    }

    public Graphic buildGraphic() {
        return buildGraphicPolyline(polyline);
    }

    public Polyline lineTo(GpsPosition position) {
        polyline.lineTo(position.toPoint());
        return polyline;
    }

    public int getId() {
        return id;
    }
}
