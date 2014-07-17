package com.agh.gopr.app.ioc.provider;

import com.agh.gopr.app.ui.fragment.MapFragment;
import com.agh.gopr.app.ui.fragment.MapFragment_;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class MapFragmentProvider implements Provider<MapFragment> {

    private MapFragment mapFragment = MapFragment_.builder().build();

    @Override
    public MapFragment get() {
        return mapFragment;
    }
}
