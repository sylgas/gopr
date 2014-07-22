package com.agh.gopr.app.service;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

import org.json.JSONTokener;

import roboguice.RoboGuice;

public class GpsGetLocationsService {

    private static final String TAG = "GpsGetLocationsService";

    @Inject
    private RequestService requestService;

    @Inject
    public GpsGetLocationsService(Context context) {
        RoboGuice.injectMembers(context, this);
        //requestService.init(context, new GetCallback());
    }

    public void getPositions() {
    }


    private static class GetCallback implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            JSONTokener tokener = new JSONTokener(json);
            //JSONObject root = new JSONObject(tokener);
        }

        @Override
        public void onError(Throwable error) {
            Log.e(TAG, "Could not load Locations");
        }
    }
}
