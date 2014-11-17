package com.agh.gopr.app.service.connection;

import android.content.Context;
import android.content.IntentFilter;

import com.agh.gopr.app.common.SettingsAlertDialog;

import javax.inject.Inject;

import roboguice.RoboGuice;

public class ConnectionService {

    @Inject
    private SettingsAlertDialog alertDialog;

    @Inject
    private NetworkEnabledReceiver networkEnabledReceiver;

    private final Context context;

    @Inject
    public ConnectionService(Context context) {
        this.context = context;
        RoboGuice.injectMembers(context, this);
    }

    public boolean isConnected() {
        return networkEnabledReceiver.isConnected();
    }

    public void registerNetworkEnabledListener(NetworkEnabledReceiver.INetworkEnabledListener listener) {
        if (listener != null) {
            context.registerReceiver(networkEnabledReceiver, createFilters());
            networkEnabledReceiver.addNetworkEnabledListener(listener);
        }
    }

    public void unregisterNetworkEnabledListener(NetworkEnabledReceiver.INetworkEnabledListener listener) {
        if (listener != null) {
            context.unregisterReceiver(networkEnabledReceiver);
            networkEnabledReceiver.removeNetworkEnabledListener(listener);
        }
    }

    public void showDialog() {
        alertDialog.showSettingsAlert(SettingsAlertDialog.Setting.NETWORK);
    }

    private IntentFilter createFilters() {
        final IntentFilter filters = new IntentFilter();
        filters.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        return filters;
    }

}
