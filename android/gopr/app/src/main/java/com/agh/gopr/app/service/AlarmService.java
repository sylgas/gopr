package com.agh.gopr.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.inject.Inject;

import roboguice.receiver.RoboBroadcastReceiver;

public class AlarmService extends RoboBroadcastReceiver {

    public static final String ACTION_ALARM = "com.agh.gopr.app.service.ACTION_ALARM";
    private static final long TIME_BW_ALARMS = 1000 * 5;
    @Inject
    private AlarmManager alarmManager;

    @Inject
    private GpsPostPositionsService sendLocationService;

    @Inject
    private Context context;

    private LocationListener listener = new OnEnableGpsListener();

    @Override
    protected void handleReceive(Context context, Intent intent) {
        handle();
    }

    public void start() {
        sendLocationService.startService(context);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                TIME_BW_ALARMS,
                TIME_BW_ALARMS, pendingIntent(context));
    }

    private void handle() {
        sendLocationService.sendPositions();
    }

    public void stop() {
        alarmManager.cancel(pendingIntent(context));
    }

    private PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ALARM);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public GpsPostPositionsService getSendLocationService() {
        return sendLocationService;
    }


    public LocationListener getListener() {
        return listener;
    }

    public class AlarmEvent {
    }

    private class OnEnableGpsListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            start();
        }

        @Override
        public void onProviderDisabled(String s) {
            stop();
        }
    }
}
