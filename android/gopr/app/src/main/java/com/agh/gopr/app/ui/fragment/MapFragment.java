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
import com.agh.gopr.app.service.GpsLocationService;
import com.agh.gopr.app.service.RequestService;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Transformation2D;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.Symbol;
import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;

import com.esri.android.map.MapView;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import roboguice.event.EventManager;
import roboguice.fragment.RoboFragment;

/**
 * A placeholder fragment containing a simple view.
 */
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

    @Inject
    private GpsLocationService gpsLocationService;

    @Inject
    private RequestService requestService;

    @Pref
    protected Preferences_ preferences;

    private Layer territoriesLayer;
    private GraphicsLayer markersLayer;
    private File mapFile;
    private MarkerListener markerListener = new MarkerListener();
    private LayerJSONHandler handler = new LayerJSONHandler();
    private Map<String, Integer> markers = new HashMap<String, Integer>();

    @AfterViews
    protected void init() {
        try {
            ensureExternalMemoryIsMounted();
            ensureMapExists();
        } catch (MapFileException e) {
            display(e.getMessage());
            return;
        }
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

        //map.centerAndZoom(50.03, 19.56, 5);
/*        demoDataFile = Environment.getExternalStorageDirectory();
        offlineDataSDCardDirName = GOPRMobile.getAppDirectory().getName();
        String basemap = demoDataFile + File.separator + offlineDataSDCardDirName + File.separator + FILENAME;
*/

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
                                e.printStackTrace();
                            }
                            ls.start();
                            gpsLocationService.start(context, preferences.login().get(), ls);
                        }
                    }
                }
        );

    }

    private void sendRequestForTerritoriesLayer() {
        requestService.init(context, handler);
        requestService.sendUpdateRequest();
    }

    private void loadMarkersLayer() {
        markersLayer = new GraphicsLayer();
        map.addLayer(markersLayer);
    }

    private void loadTerritoriesLayer(JsonParser parser, String layerDefinition) throws Exception {
        Log.d(TAG, "Loading territories layer...");
        ArcGISFeatureLayer.Options layerOptions = new ArcGISFeatureLayer.Options();
        String[] table = {"*"};
        layerOptions.outFields = table;
        layerOptions.mode = ArcGISFeatureLayer.MODE.SNAPSHOT;

        FeatureSet featureSet = FeatureSet.fromJson(parser);

        territoriesLayer = new ArcGISFeatureLayer(layerDefinition, featureSet, layerOptions);
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
        JSONTokener tokener = new JSONTokener(json);
        JSONObject root = new JSONObject(tokener);
        JSONObject jsonFS = root.getJSONObject("jsonFS");
        String jsonLD = root.getJSONObject("jsonLD").toString();

        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createJsonParser(jsonFS.toString());
        parser.nextToken();
        loadTerritoriesLayer(parser, jsonLD);
    }

    private class MarkerListener implements GpsLocationService.PositionsListener {

        @Override
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

    private class LayerJSONHandler implements RequestService.JSONHandler {

        @Override
        public void handle(String json) {
            try {
                readLayerFromJson(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class StartMessengerEvent {
    }
}