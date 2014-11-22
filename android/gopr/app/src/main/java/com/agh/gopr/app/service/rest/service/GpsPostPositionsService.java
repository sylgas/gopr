package com.agh.gopr.app.service.rest.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.agh.gopr.app.common.PreferenceHelper;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.common.SettingsAlertDialog;
import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.PositionService;
import com.agh.gopr.app.service.rest.RequestService;
import com.agh.gopr.app.service.rest.service.method.RestMethod;
import com.google.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.util.Date;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.util.Ln;

public class GpsPostPositionsService {

    private static final long MIN_TIME_BT_UPDATES_IN_MILLIS = 5000;

    private static final float MIN_DISTANCE_BT_UPDATES_IN_METERS = 7;

    @Inject
    private LocationManager locationManager;

    @Inject
    private SettingsAlertDialog settingsAlertDialog;

    @Inject
    private PositionService positionService;

    @Inject
    private Preferences_ preferences;

    private final RequestService.HttpCallback callback = new PostCallback();

    private final Context context;

    @Inject
    public GpsPostPositionsService(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
        if (!gpsEnabled()) {
            settingsAlertDialog.showSettingsAlert(SettingsAlertDialog.Setting.GPS);
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, MIN_TIME_BT_UPDATES_IN_MILLIS,
                MIN_DISTANCE_BT_UPDATES_IN_METERS, new GpsLocationListener());
    }

    public boolean gpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private class PostCallback implements RequestService.HttpCallback {

        @Override
        public synchronized void handle(String json) {
            Position position = positionService.getMostRecentPosition();
            preferences.lastPositionPostTime().put(position.getDate().getTime());
        }

        @Override
        public void onError(Throwable error) {
            Ln.e("Could not post positions: " + error.getMessage());
        }
    }

    private class GpsLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            addPositionIfGpsEnabled(location);
            if (positionService.getNotPostedPositionsCount() == 0) {
                return;
            }
            List<Position> positions = positionService.getNotPostedPositions();

            try {
                Ln.d("Trying to post %d positions...", positions.size());
                JSONObject jsonObject = JsonHelper.createJsonFromPositions(preferences.userId().get(), positions);

                String actionId = preferences.actionId().get();
                try {
                    RestMethod.POST_POINTS.run(context, callback, actionId, jsonObject.toString());
                } catch (MethodException e) {
                    Ln.e("Could not post points: %s", e.getMessage());
                }

                positions.clear();
            } catch (JSONException e) {
                Ln.e("Could not create JSON");
            }
        }

        private void addPositionIfGpsEnabled(Location location) {
            if (gpsEnabled()) {
                Position position = Position.fromLocation(location);
                if (position == null) {
                    Ln.d("Position is not established");
                    return;
                }
                position.setUserId(preferences.userId().get());
                position.setActionId(preferences.actionId().get());
                position.setDate(new Date(System.currentTimeMillis()));
                positionService.save(position);
            } else {
                Ln.d("Gps is not enabled");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}