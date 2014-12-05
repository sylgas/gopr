package com.agh.gopr.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.agh.gopr.app.service.rest.service.IMethodService;
import com.google.inject.Inject;

import java.util.Set;

import roboguice.event.EventManager;
import roboguice.inject.ContextSingleton;
import roboguice.receiver.RoboBroadcastReceiver;

@ContextSingleton
public class IntervalSynchronizationService extends RoboBroadcastReceiver {

    public static final String ACTION_ALARM = "com.agh.gopr.app.service.ACTION_ALARM";
    private static final long TIME_BW_ALARMS_IN_MILLIS = 7000;

    @Inject
    private AlarmManager alarmManager;

    @Inject
    private Context context;

    @Inject
    private Set<IMethodService> services;

    @Inject
    private EventManager eventManager;

    @Inject
    private PowerManager powerManager;

    @Override
    protected void handleReceive(Context context, Intent intent) {
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        handle();

        wakeLock.release();
    }

    public void start() {
        startServices();
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                TIME_BW_ALARMS_IN_MILLIS,
                TIME_BW_ALARMS_IN_MILLIS, pendingIntent(context));
    }

    private void handle() {
        for (IMethodService service : services) {
            service.handle();
        }
        handleFinished();
    }

    public void stop() {
        alarmManager.cancel(pendingIntent(context));
    }

    private void startServices() {
        for (IMethodService service : services) {
            service.init();
        }
        handleFinished();
    }

    private void handleFinished() {
        eventManager.fire(new HandleFinishedEvent());
    }

    private PendingIntent pendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ALARM);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public class HandleFinishedEvent {
    }
}
