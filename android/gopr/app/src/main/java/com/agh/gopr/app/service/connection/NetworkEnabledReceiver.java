package com.agh.gopr.app.service.connection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import roboguice.receiver.RoboBroadcastReceiver;

public class NetworkEnabledReceiver extends RoboBroadcastReceiver {

    private final List<INetworkEnabledListener> networkEnabledListeners = new CopyOnWriteArrayList<INetworkEnabledListener>();

    @Inject
    private ConnectivityManager connectivityManager;

    @Override
    public void handleReceive(Context context, Intent intent) {
        if (isConnected()) {
            for (INetworkEnabledListener listener : networkEnabledListeners) {
                listener.onEnable();
            }
        }
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void addNetworkEnabledListener(INetworkEnabledListener listener) {
        networkEnabledListeners.add(listener);
    }

    public void removeNetworkEnabledListener(INetworkEnabledListener listener) {
        networkEnabledListeners.remove(listener);
    }

    public interface INetworkEnabledListener {
        void onEnable();
    }
}
