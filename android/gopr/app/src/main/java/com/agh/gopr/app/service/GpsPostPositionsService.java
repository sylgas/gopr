package com.agh.gopr.app.service;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.agh.gopr.app.common.SettingsAlertDialog;
import com.agh.gopr.app.map.GpsPosition;
import com.agh.gopr.app.service.rest.RestMethod;
import com.agh.gopr.app.service.rest.exception.MethodException;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

@Singleton
public class GpsPostPositionsService {
    private static final String TAG = "GpsPostLocationService";
    private final List<GpsPosition> positions = new ArrayList<GpsPosition>();
    private final RequestService.HttpCallback callback = new PostCallback();
    @Inject
    private LocationManager locationManager;
    @Inject
    private MapFragment mapFragment;
    @Inject
    private ConnectivityService connectivityService;
    @Inject
    private SettingsAlertDialog settingsAlertDialog;
    private Context context;
    private boolean pending = true;

    public void startService(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
        if (!gpsEnabled()) {
            settingsAlertDialog.showSettingsAlert(SettingsAlertDialog.Setting.GPS);
        } else {
            pending = false;
        }
    }

    public boolean gpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void sendPositions() {
        if (pending) {
            Log.d(TAG, "Position request is pending...");
            return;
        }

        GpsPosition position = mapFragment.getCurrentPosition();
        if (position == null) {
            Log.d(TAG, "Position is not established");
            return;
        }

        positions.add(position);

        if (!connectivityService.isConnected()) {
            Log.d(TAG, "No connection found");
            return;
        }

        try {

            Log.d(TAG, "Trying to post positions...");
            JSONObject jsonObject = GpsPosition.createJSON(mapFragment.getName(), positions);
            pending = true;
            //TODO: actionId
            String actionId = "1";
            try {
                RestMethod.POST_POINTS.run(context, callback, actionId, jsonObject.toString());
            } catch (MethodException e) {
                pending = false;
                Log.e(TAG, "Could not post points: " + e.getMessage());
            }

            positions.clear();
        } catch (JSONException e) {
            pending = false;
            Log.e(TAG, "Could not create JSON");
            return;
        }
    }

    private class PostCallback implements RequestService.HttpCallback {

        @Override
        public synchronized void handle(String json) {
            positions.clear();
            pending = false;
        }

        @Override
        public void onError(Throwable error) {
            Log.e(TAG, "Could not send positions " + error.getMessage());
            pending = false;
        }
    }
}
