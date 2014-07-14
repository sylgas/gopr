package com.agh.gopr.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.PowerManager;
import android.util.Log;

import com.agh.gopr.app.common.SettingsAlertDialog;
import com.esri.android.map.LocationDisplayManager;
import com.esri.core.geometry.Point;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import roboguice.receiver.RoboBroadcastReceiver;

@Singleton
public class GpsLocationService extends RoboBroadcastReceiver {

    private static final String TAG = "GpsLocationService";

    private static final long TIME_BW_UPDATES = 1000 * 5;

    private JSONArray array = new JSONArray();

    @Inject
    private LocationManager locationManager;

    @Inject
    private AlarmManager alarmManager;

    @Inject
    private PowerManager powerManager;

    private LocationDisplayManager manager;
    private String name;

    @Override
    protected void handleReceive(Context context, Intent intent) {
        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        handle();

        wl.release();
    }

    public void start(Context context, String name, LocationDisplayManager manager) {
        this.manager = manager;
        this.name = name;

        SettingsAlertDialog alertDialog = new SettingsAlertDialog(context);
        if (gpsEnabled()) {
            stop(context);
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    TIME_BW_UPDATES,
                    TIME_BW_UPDATES, pendingIntent(context));
        } else {
            //locationManager.requestSingleUpdate();
            alertDialog.showSettingsAlert(SettingsAlertDialog.Setting.GPS);
            //TODO: addLocationListener, Location Listener ??
        }
    }

    private void handle() {
        if (manager == null) {
            return;
        }
        Log.d(TAG, "dupa " + (System.currentTimeMillis() / 1000));
        Log.d(TAG, "dupa " + manager.getPoint().toString());
        Log.d(TAG, "dupa " + name);
  //      positions.add(manager.getPoint());

    }

    public void stop(Context context) {
        alarmManager.cancel(pendingIntent(context));
    }

/*    private JSONObject createJSONPosition() {
        JSONObject position = new JSONObject();
        position

    }*/

    private PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent(context, GpsLocationService.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    public boolean gpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public interface PositionsListener {
        void handle(String id, Point point);
    }
}
