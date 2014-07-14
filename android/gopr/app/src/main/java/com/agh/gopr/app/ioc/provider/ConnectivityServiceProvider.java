package com.agh.gopr.app.ioc.provider;

import android.content.Context;

import com.agh.gopr.app.service.ConnectivityService_;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ConnectivityServiceProvider implements Provider<ConnectivityService_> {

    @Inject
    private Context context;

    @Override
    public ConnectivityService_ get() {
        return ConnectivityService_.getInstance_(context);
    }
}
