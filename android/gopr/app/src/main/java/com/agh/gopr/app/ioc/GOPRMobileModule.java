package com.agh.gopr.app.ioc;

import com.agh.gopr.app.ioc.provider.ConnectivityServiceProvider;
import com.agh.gopr.app.ioc.provider.RequestServiceProvider;
import com.agh.gopr.app.service.ConnectivityService;
import com.agh.gopr.app.service.RequestService;
import com.google.inject.Binder;
import com.google.inject.Module;

public class GOPRMobileModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ConnectivityService.class).toProvider(ConnectivityServiceProvider.class);
        binder.bind(RequestService.class).toProvider(RequestServiceProvider.class);
    }
}
