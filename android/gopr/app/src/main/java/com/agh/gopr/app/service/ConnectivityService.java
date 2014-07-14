package com.agh.gopr.app.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.agh.gopr.app.common.SettingsAlertDialog;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.UiThread;

import javax.inject.Inject;

import roboguice.RoboGuice;

@EBean
public class ConnectivityService {

    private static final String TAG = "ConnectivityService";

    @Inject
    private ConnectivityManager connectivityManager;

    @Inject
    private SettingsAlertDialog alertDialog;

    private Context context;

    @Inject
    public ConnectivityService(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
    }

    public boolean enableConnection() {
        if (!wifiEnabled() && !transferEnabled()) {
            Log.d(TAG, "No connection found");
            showDialog();
            return false;
        }
        return true;
    }

    @UiThread
    protected void showDialog() {
        alertDialog.showSettingsAlert(SettingsAlertDialog.Setting.NETWORK);
    }

    private boolean wifiEnabled() {
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }

    private boolean transferEnabled() {
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }
}
