package com.agh.gopr.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.agh.gopr.app.service.rest.IMethodService;
import com.google.inject.Inject;

import java.util.Set;

import roboguice.receiver.RoboBroadcastReceiver;

public class AlarmService extends RoboBroadcastReceiver {

    public static final String ACTION_ALARM = "com.agh.gopr.app.service.ACTION_ALARM";
    private static final long TIME_BW_ALARMS = 1000 * 5;

    @Inject
    private AlarmManager alarmManager;

    @Inject
    private Context context;

    @Inject
    private Set<IMethodService> services;

    @Override
    protected void handleReceive(Context context, Intent intent) {
        handle();
    }

    public void start() {
        startServices();
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                TIME_BW_ALARMS,
                TIME_BW_ALARMS, pendingIntent(context));
    }

    private void handle() {
        for (IMethodService service : services) {
            service.handle();
        }
    }

    private void startServices() {
        for (IMethodService service : services) {
            service.start(context);
        }
    }

    public void stop() {
        alarmManager.cancel(pendingIntent(context));
    }

    private PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ALARM);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
