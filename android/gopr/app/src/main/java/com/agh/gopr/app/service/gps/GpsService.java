package com.agh.gopr.app.service.gps;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.service.PositionService;
import com.google.inject.Inject;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Date;

import roboguice.service.RoboService;
import roboguice.util.Ln;

@EService
public class GpsService extends RoboService {
    private static final long MIN_TIME_BT_UPDATES_IN_MILLIS = 7000;
    private static final float MIN_DISTANCE_BT_UPDATES_IN_METERS = 0;
    public static final String ACTION_ID = GpsService.class.getCanonicalName();

    @Pref
    protected Preferences_ preferences;

    @Inject
    private PositionService positionService;

    @Inject
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, MIN_TIME_BT_UPDATES_IN_MILLIS,
                MIN_DISTANCE_BT_UPDATES_IN_METERS, new GpsLocationListener());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    private class GpsLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Ln.v("Adding position");
            Position position = Position.fromLocation(location);
            Ln.d("Added position to post " + position.toString());
            position.setUserId(preferences.userId().get());
            position.setDate(new Date(System.currentTimeMillis()));
            positionService.save(position);
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
