package com.agh.gopr.app.ioc;

import com.agh.gopr.app.ioc.provider.ConnectivityServiceProvider;
import com.agh.gopr.app.ioc.provider.MapFragmentProvider;
import com.agh.gopr.app.ioc.provider.RequestServiceProvider;
import com.agh.gopr.app.service.ConnectivityService;
import com.agh.gopr.app.service.RequestService;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.google.inject.Binder;
import com.google.inject.Module;

public class GOPRMobileModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ConnectivityService.class).toProvider(ConnectivityServiceProvider.class);
        binder.bind(RequestService.class).toProvider(RequestServiceProvider.class);
        binder.bind(MapFragment.class).toProvider(MapFragmentProvider.class);
    }
}
