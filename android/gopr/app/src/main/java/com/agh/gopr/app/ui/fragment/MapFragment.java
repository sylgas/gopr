package com.agh.gopr.app.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.agh.gopr.app.GOPRMobile;
import com.agh.gopr.app.R;
import com.agh.gopr.app.common.PreferenceHelper;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.exception.MapFileException;
import com.agh.gopr.app.map.GpsPosition;
import com.agh.gopr.app.service.AlarmService;
import com.agh.gopr.app.service.RequestService;
import com.agh.gopr.app.service.rest.RestMethod;
import com.agh.gopr.app.service.rest.exception.MethodException;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.event.EventManager;
import roboguice.fragment.RoboFragment;

@EFragment(R.layout.map_fragment)
@OptionsMenu(R.menu.map_menu)
public class MapFragment extends RoboFragment {

    private static final String TAG = "MapFragment";
    private static final String URL = "https://www.google.com";

    @StringRes
    protected String mapFileName;

    @StringRes
    protected String noExternalMemoryError;

    @StringRes
    protected String mapFileNotFoundError;

    @ViewById
    protected MapView map;

    @Inject
    protected EventManager eventManager;

    @Inject
    protected Context context;
    @Pref
    protected Preferences_ preferences;
    @Inject
    private AlarmService alarmService;
    private GraphicsLayer territoriesLayer;
    private GraphicsLayer markersLayer;
    private File mapFile;
    private MarkerListener markerListener = new MarkerListener();
    private LayerJSONHandler layerJSONHandler = new LayerJSONHandler();
    private Map<String, Integer> markers = new HashMap<String, Integer>();

    @AfterViews
    protected void init() {

        /*try {
            ensureExternalMemoryIsMounted();
            ensureMapExists();
        } catch (MapFileException e) {
            display(e.getMessage());
            return;
        }*/
        sendRequestForTerritoriesLayer();
        loadMarkersLayer();
        loadBaseLayer();

       /* try {
            db = new MapGeoDatabase(map);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }*/



/*
        );*//*

/*        demoDataFile = Environment.getExternalStorageDirectory();
        offlineDataSDCardDirName = GOPRMobile.getAppDirectory().getName();
        String basemap = demoDataFile + File.separator + offlineDataSDCardDirName + File.separator + FILENAME;
*/

    }

    public GpsPosition getCurrentPosition() {
        return GpsPosition.fromPoint(map.getLocationDisplayManager().getPoint());
    }

    public String getName() {
        return preferences.login().get();
    }

    public void onPause() {
        super.onPause();
        map.pause();
    }

    public void onResume() {
        super.onResume();
        map.unpause();
        if (preferences.showLayer().get()) {
            addTerritoriesLayer();
        } else {
            removeTerritoriesLayer();
        }
    }

    @OptionsItem(R.id.messenger)
    protected void messenger() {
        eventManager.fire(new StartMessengerEvent());
    }

    @OptionsItem(R.id.download)
    protected void download() {
        loadSite();
    }

    @OptionsItem(R.id.refresh)
    protected void refresh() {
        init();
    }

    private void removeTerritoriesLayer() {
        if (territoriesLayer != null) {
            map.removeLayer(territoriesLayer);
        }
    }

    private void addTerritoriesLayer() {
        if (territoriesLayer != null) {
            map.addLayer(territoriesLayer);
        }
    }

    private void loadSite() {
        Uri uri = Uri.parse(URL);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void loadBaseLayer() {
        Log.d(TAG, String.format("Loading basemap from [%s]", PreferenceHelper.getMapSite(preferences)));
        map.addLayer(new ArcGISTiledMapServiceLayer(PreferenceHelper.getMapSite(preferences)));
        map.setOnStatusChangedListener(
                new OnStatusChangedListener() {
                    public void onStatusChanged(Object source, STATUS status) {
                        if (source == map && status == STATUS.INITIALIZED) {
                            LocationDisplayManager ls = map.getLocationDisplayManager();
                            try {
                                MarkerSymbol symbol = buildSymbol(R.drawable.map_marker_green);
                                ls.setDefaultSymbol(symbol);
                                ls.setCourseSymbol(symbol);
                            } catch (Exception e) {
                                Log.d(TAG, "Could not create icon");
                            }
                            ls.start();
                            alarmService.start();
                            center();
                        }
                    }
                }
        );
    }

    private void center() {
        map.centerAt(new Point(2214749.0606268025, 6460923.093105682), true);
        map.zoomin();
    }

    private void sendRequestForTerritoriesLayer() {
        //TODO: change it
        String actionId = "1";

        try {
            RestMethod.GET_LAYER.run(context, layerJSONHandler, actionId);
        } catch (MethodException e) {
            Log.e(TAG, e.getMessage());
        }
        //RestMethod.GET_ALL_POINTS.run(baseUrl, actionId);
    }

    private void loadMarkersLayer() {
        markersLayer = new GraphicsLayer();
        map.addLayer(markersLayer);
    }

    private void loadTerritoriesLayer(List<Graphic> areas) throws Exception {
        Log.d(TAG, "Loading territories layer...");

        territoriesLayer = new GraphicsLayer();
        for (Graphic g : areas) {
            territoriesLayer.addGraphic(g);
        }
        map.addLayer(territoriesLayer);
    }

    private void ensureMapExists() throws MapFileException {
        mapFile = new File(GOPRMobile.getAppDirectory(), mapFileName);
        Log.d(TAG, String.format("Checking if file[%s] exists.", mapFile.getAbsolutePath()));

        if (!mapFile.exists()) {
            throw new MapFileException(String.format(mapFileNotFoundError, mapFile.getAbsolutePath()));
        }
    }

    private void ensureExternalMemoryIsMounted() throws MapFileException {
        if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new MapFileException(noExternalMemoryError);
        }
    }

    private void display(String msg) {
        new AlertDialog.Builder(context).setMessage(msg).show();
    }

    private PictureMarkerSymbol buildSymbol(int id) {
        Drawable drawable = getResources().getDrawable(id);
        PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setOffsetY(20);
        return symbol;
    }

    private Graphic buildMarker(Point point) {
        return new Graphic(point, buildSymbol(R.drawable.map_marker_azure));
    }

    private void readLayerFromJson(String json) throws Exception {
        List<Graphic> areas = new ArrayList<Graphic>();

        JSONTokener tokener = new JSONTokener(json);
        JSONObject root = new JSONObject(tokener);

        JSONArray graphicArray = root.getJSONArray("geometries");

        JsonFactory factory = new JsonFactory();

        for (int i = 0; i < graphicArray.length(); i++) {
            String cos = graphicArray.getJSONObject(i).getString("area");
            JsonParser parser = factory.createJsonParser(graphicArray.getJSONObject(i).getString("area"));
            MapGeometry mapGeometry = GeometryEngine.jsonToGeometry(parser);

            Graphic newArea = new Graphic(mapGeometry.getGeometry(),
                    new SimpleFillSymbol(makeColor(), SimpleFillSymbol.STYLE.SOLID));
            areas.add(newArea);
        }

        loadTerritoriesLayer(areas);
    }

    private int makeColor() {
        //Grey with high transparency
        return Color.argb(30, 58, 58, 58);
    }

    public static class StartMessengerEvent {
    }

    private class MarkerListener {

        public void handle(String id, Point point) {
            if (!markers.containsKey(id)) {
                Graphic marker = buildMarker(point);
                markers.put(id, marker.getUid());
                //markersLayer = new GraphicsLayer();
                markersLayer.addGraphic(marker);
                //map.addLayer(markersLayer);
            } else {
                Graphic marker = markersLayer.getGraphic(markers.get(id));
                Point p = (Point) marker.getGeometry();
                p.setXY(point.getX(), point.getY());
            }

        }
    }

    private class LayerJSONHandler implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            try {
                readLayerFromJson(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable error) {
            Log.e(TAG, "ConnectionError could not receive JSON");
        }
    }

}