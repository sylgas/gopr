package com.agh.gopr.app.common;

import android.content.Context;

import com.google.inject.Inject;

import roboguice.RoboGuice;
import roboguice.event.EventManager;

public class ApplicationEventManagerConnector {

    @Inject
    private EventManager eventManager;

    public ApplicationEventManagerConnector(Context context) {
        RoboGuice.injectMembers(context.getApplicationContext(), this);
    }

    public EventManager get() {
        return eventManager;
    }
}
