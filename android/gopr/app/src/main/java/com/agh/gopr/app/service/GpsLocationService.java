package com.agh.gopr.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.agh.gopr.app.common.SettingsAlertDialog;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.esri.core.geometry.Point;
import com.google.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import roboguice.receiver.RoboBroadcastReceiver;

public class GpsLocationService extends RoboBroadcastReceiver {

    private static final String TAG = "GpsLocationService";
    private static final long TIME_BW_UPDATES = 1000 * 5;
    public static final String ACTION_SENT = "com.agh.gopr.app.service.ACTION_SENT";

    @Inject
    private LocationManager locationManager;

    @Inject
    private AlarmManager alarmManager;

    @Inject
    private MapFragment mapFragment;

    private JSONObject positions = new JSONObject();

    @Override
    protected void handleReceive(Context context, Intent intent) {
        handle();
    }

    public void start(Context context){
        if (gpsEnabled()) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    TIME_BW_UPDATES,
                    TIME_BW_UPDATES, pendingIntent(context));
        } else {
            //locationManager.requestSingleUpdat
            SettingsAlertDialog alertDialog = new SettingsAlertDialog(context);
            alertDialog.showSettingsAlert(SettingsAlertDialog.Setting.GPS);
            //TODO: addLocationListener, Location Listener ??
        }
    }

    private void handle() {
        if (!mapFragment.isInitialized()) {
           return;
        }
    }

    public void stop(Context context) {
        alarmManager.cancel(pendingIntent(context));
    }

    private JSONObject createJSONPosition(String name, Point point) throws JSONException {
        JSONObject position = new JSONObject();
        position.put("id", name);
        JSONArray positions = new JSONArray();
        positions.put(toJson(point));
        position.put("positions", positions);
        return position;
    }

    private JSONObject toJson(Point point) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("x", point.getX());
        object.put("y", point.getY());
        return object;
    }

    private PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent(context, GpsLocationService.class);
        intent.setAction(ACTION_SENT);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    public boolean gpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public interface PositionsListener {
        void handle(String id, Point point);
    }
}
