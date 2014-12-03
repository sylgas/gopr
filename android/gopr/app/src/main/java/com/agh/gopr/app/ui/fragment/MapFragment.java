package com.agh.gopr.app.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.agh.gopr.app.R;
import com.agh.gopr.app.common.ApplicationEventManagerConnector;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.GpsPolyline;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.IntervalSynchronizationService;
import com.agh.gopr.app.service.PositionService;
import com.agh.gopr.app.service.connection.ConnectionService;
import com.agh.gopr.app.service.connection.NetworkEnabledReceiver;
import com.agh.gopr.app.service.rest.RequestService;
import com.agh.gopr.app.service.rest.service.method.RestMethod;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.google.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.event.EventListener;
import roboguice.event.EventManager;
import roboguice.fragment.RoboFragment;
import roboguice.util.Ln;

@EFragment(R.layout.map_fragment)
@OptionsMenu(R.menu.map_menu)
public class MapFragment extends RoboFragment {

    private static final String URL = "https://www.google.com";

    @ViewById
    protected MapView map;

    @Pref
    protected Preferences_ preferences;

    @Inject
    private EventManager eventManager;

    @Inject
    private Context context;

    @Inject
    private ConnectionService connectionService;

    @Inject
    private PositionService positionService;

    @Inject
    private IntervalSynchronizationService intervalSynchronizationService;

    private final LayerJSONHandler layerJSONHandler = new LayerJSONHandler();
    private final Map<String, Integer> markers = new HashMap<String, Integer>();
    private final Map<String, GpsPolyline> polylines = new HashMap<String, GpsPolyline>();
    private final Map<LayerType, Integer> layers = new HashMap<LayerType, Integer>();
    private final ViewChangedListenener viewChangedListenener = new ViewChangedListenener();

    private long lastReceivedPositionUpdate = 0L;

    private long lastUserPositionUpdate = 0L;
    private ApplicationEventManagerConnector applicationEventManager;
    private GraphicsLayer territoriesLayer;
    private GraphicsLayer markersLayer;
    private GraphicsLayer polylinesLayer;
    private NetworkEnabledListener networkEnabledListener;

    private void init() {
        if (map.getLayers().length == 0) {
            layers.clear();
            markers.clear();
            polylines.clear();
            sendRequestForTerritoriesLayer();
            loadBaseLayer();
            loadPolylinesLayer();
            loadMarkersLayer();
        } else {
            addOrRemoveTerritoriesLayerIfNeeded();
        }
    }

    @AfterViews
    public void setUp() {
        applicationEventManager = new ApplicationEventManagerConnector(context);
        applicationEventManager.get().registerObserver(IntervalSynchronizationService.HandleFinishedEvent.class, viewChangedListenener);
    }

    @Override
    public void onResume() {
        super.onResume();
        initIfNeeded();
        map.unpause();
    }

    private void initIfNeeded() {
        if (connectionService.isConnected()) {
            init();
        } else {
            if (networkEnabledListener == null) {
                networkEnabledListener = new NetworkEnabledListener();
                connectionService.showDialog();
            }
            registerNetworkEnabledListenerIfNeeded();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterNetworkEnabledListenerIfNeeded();
        applicationEventManager.get().unregisterObserver(IntervalSynchronizationService.HandleFinishedEvent.class, viewChangedListenener);
    }

    @OptionsItem(R.id.messenger)
    protected void messenger() {
        eventManager.fire(new StartMessengerEvent());
    }

/*
    @OptionsItem(R.id.download)
    protected void download() {
        loadSite();
    }*/

    private void registerNetworkEnabledListenerIfNeeded() {
        if (!networkEnabledListener.isRegistered()) {
            connectionService.registerNetworkEnabledListener(networkEnabledListener);
            networkEnabledListener.setRegistered(true);
        }
    }

    private void unregisterNetworkEnabledListenerIfNeeded() {
        if (networkEnabledListener != null && networkEnabledListener.isRegistered()) {
            connectionService.unregisterNetworkEnabledListener(networkEnabledListener);
            networkEnabledListener.setRegistered(false);
        }
    }

    private void sendRequestForTerritoriesLayer() {
        String actionId = preferences.actionId().get();

        try {
            RestMethod.GET_LAYER.run(context, layerJSONHandler, actionId);
        } catch (MethodException e) {
            Ln.e(e.getMessage());
        }
    }

    private void loadBaseLayer() {
        Ln.d("Loading basemap from [%s]", preferences.mapSite().get());
        int id = map.addLayer(new ArcGISTiledMapServiceLayer(preferences.mapSite().get()));
        layers.put(LayerType.BASE_LAYER, id);
        map.setOnStatusChangedListener(new MapInitializedListener());
    }

    private void loadPolylinesLayer() {
        polylinesLayer = new GraphicsLayer();
        int id = map.addLayer(polylinesLayer);
        layers.put(LayerType.POLYLINES_LAYER, id);
    }

    private void loadMarkersLayer() {
        markersLayer = new GraphicsLayer();
        int id = map.addLayer(markersLayer);
        layers.put(LayerType.MARKERS_LAYER, id);
    }

    public String getUserId() {
        //TODO: Replace by primitive authorisation system??
        return preferences.userId().get();
    }

    private void addTerritoriesLayerIfNeeded() {
        if (preferences.showLayer().get() && !territoriesLayerIsAddedToMap()) {
            addTerritoriesLayer();
        }
    }

    private void addOrRemoveTerritoriesLayerIfNeeded() {
        if (!preferences.showLayer().get()) {
            if (territoriesLayerIsAddedToMap()) {
                map.removeLayer(territoriesLayer);
                layers.remove(LayerType.TERRITORIES_LAYER);
            }
        } else {
            if (!territoriesLayerIsAddedToMap()) {
                addTerritoriesLayer();
            }
        }
    }

    private boolean territoriesLayerIsAddedToMap() {
        return layers.containsKey(LayerType.TERRITORIES_LAYER) &&
                map.getLayer(layers.get(LayerType.TERRITORIES_LAYER)) != null;
    }

    private void addTerritoriesLayer() {
        if (territoriesLayer != null) {
            int id = map.addLayer(territoriesLayer);
            layers.put(LayerType.TERRITORIES_LAYER, id);
        }
    }

/*
    private void loadSite() {
        Uri uri = Uri.parse(URL);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
*/

    private void center() {
        map.centerAt(new Point(2214749.0606268025, 6460923.093105682), true);
        map.zoomin();
    }

    private void loadTerritoriesLayer(List<Graphic> areas) throws Exception {
        Ln.d("Loading territories layer...");

        territoriesLayer = new GraphicsLayer();
        for (Graphic g : areas) {
            territoriesLayer.addGraphic(g);
        }
        addTerritoriesLayerIfNeeded();
    }

    private void updateReceivedPositions() {
        List<Position> positions = positionService.getReceivedPositionsFrom(lastReceivedPositionUpdate);
        if (positions.size() > 0) {
            draw(positions, R.drawable.map_marker_azure);
            lastReceivedPositionUpdate = positions.get(positions.size() - 1).getDate().getTime();
        }
    }

    private void updateUserPositions() {
        List<Position> positions = positionService.getPositionsFrom(lastUserPositionUpdate);
        if (positions.size() > 0) {
            draw(positions, R.drawable.map_marker_green);
            lastUserPositionUpdate = positions.get(positions.size() - 1).getDate().getTime();
        }
    }

    private void draw(List<Position> positions, int imageResId) {
        for (Position position : positions) {
            drawPosition(position.getUserId(), position, imageResId);
        }
    }

    private void drawPosition(String id, Position position, int imageResId) {
        Ln.d("Marker: [%s]", id);

        if (!markers.containsKey(id)) {
            addMarker(id, buildMarker(position, imageResId));
            initiatePolyline(id, position);
        } else {
            updateMarker(id, buildMarker(position, imageResId));
            updatePolyline(id, position);
        }
    }

    private void addMarker(String id, Graphic marker) {
        int markerId = markersLayer.addGraphic(marker);
        markers.put(id, markerId);
    }

    private void updateMarker(String id, Graphic marker) {
        int markerId = markers.get(id);
        markersLayer.updateGraphic(markerId, marker);
    }


    private PictureMarkerSymbol buildSymbol(int id) {
        Drawable drawable = getResources().getDrawable(id);
        PictureMarkerSymbol symbol = new PictureMarkerSymbol(drawable);
        symbol.setOffsetY(20);
        return symbol;
    }

    private Graphic buildMarker(Position position, int imageResId) {
        return new Graphic(position.toPoint(map.getSpatialReference()), buildSymbol(imageResId));
    }

    private void updatePolyline(String id, Position position) {
        GpsPolyline gpsPolyline = polylines.get(id);
        Polyline polyline = gpsPolyline.lineTo(position, map.getSpatialReference());
        polylinesLayer.updateGraphic(gpsPolyline.getId(), polyline);
    }

    private void initiatePolyline(String id, Position position) {
        Polyline polyline = GpsPolyline.buildPolyline(position, map.getSpatialReference());
        int polylineID = polylinesLayer.addGraphic(GpsPolyline.buildGraphicPolyline(polyline));
        polylines.put(id, new GpsPolyline(polyline, polylineID));
    }

    public static class StartMessengerEvent {
    }

    private class NetworkEnabledListener implements NetworkEnabledReceiver.INetworkEnabledListener {

        private boolean isRegistered;

        public boolean isRegistered() {
            return isRegistered;
        }

        public void setRegistered(boolean isRegistered) {
            this.isRegistered = isRegistered;
        }

        @Override
        public void onEnable() {
            init();
            unregisterNetworkEnabledListenerIfNeeded();
        }
    }

    private class ViewChangedListenener implements EventListener<IntervalSynchronizationService.HandleFinishedEvent> {

        @Override
        public void onEvent(IntervalSynchronizationService.HandleFinishedEvent event) {
            if (map.getLayers().length > 0) {
                updateUserPositions();
                updateReceivedPositions();
            }
        }
    }

    private class MapInitializedListener implements OnStatusChangedListener {

        @Override
        public void onStatusChanged(Object source, STATUS status) {
            if (source == map && status == STATUS.INITIALIZED) {
                intervalSynchronizationService.start();
                center();
            }
        }
    }

    private class LayerJSONHandler implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            try {
                List<Graphic> areas = JsonHelper.readLayer(json);
                loadTerritoriesLayer(areas);
            } catch (Exception e) {
                Ln.e(e, "Could not add territories layer");
            }
        }

        @Override
        public void onError(Throwable error) {
            Ln.e("ConnectionError could not receive JSON");
        }
    }

    private enum LayerType {
        BASE_LAYER,
        TERRITORIES_LAYER,
        POLYLINES_LAYER,
        MARKERS_LAYER
    }

}