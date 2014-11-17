package com.agh.gopr.app.ioc.provider;

import android.content.Context;

import com.agh.gopr.app.common.Preferences_;
import com.google.inject.Provider;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class PreferencesProvider implements Provider<Preferences_> {
    @Inject
    private Context context;

    @Override
    public Preferences_ get() {
        return new Preferences_(context);
    }
}

