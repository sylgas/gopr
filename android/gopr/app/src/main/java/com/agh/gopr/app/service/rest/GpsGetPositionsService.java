package com.agh.gopr.app.service.rest;

import android.content.Context;
import android.util.Log;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.GpsPosition;
import com.agh.gopr.app.method.RestMethod;
import com.agh.gopr.app.service.RequestService;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import roboguice.RoboGuice;

@Singleton
public class GpsGetPositionsService implements IMethodService {

    private static final String TAG = "GpsGetLocationsService";

    @Inject
    protected MapFragment mapFragment;

    private Context context;
    private RequestService.HttpCallback callback = new GetCallback();

    @Override
    public void start(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
        try {
            RestMethod.GET_ALL_POINTS.run(context, callback, "1");
        } catch (MethodException e) {
            Log.e(TAG, "Could not get positions: " + e.getMessage());
        }
    }

    @Override
    public void handle() {
        if (context == null) {
            return;
        }
        try {
            RestMethod.GET_POINTS.run(context, callback, "1", String.valueOf(System.currentTimeMillis()));
        } catch (MethodException e) {
            Log.e(TAG, "Could not get positions: " + e.getMessage());
        }
    }


    private class GetCallback implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            try {
                JSONTokener tokener = new JSONTokener(json);
                JSONArray root = new JSONArray(tokener);
                for (int i = 0; i < root.length(); i++) {
                    readUser(root.getJSONObject(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void readUser(JSONObject root) throws JSONException {
            String id = root.getString("userId");
            JSONArray positions = root.getJSONArray("positions");
            for (int i = 0; i < positions.length(); i++) {
                GpsPosition gpsPosition = GpsPosition.fromJson(positions.getJSONObject(i));
                mapFragment.handle(id, gpsPosition);
            }
        }

        @Override
        public void onError(Throwable error) {
            Log.e(TAG, "Could not load Locations: " + error.getMessage());
        }
    }
}
