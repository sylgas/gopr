package com.agh.gopr.app.service.rest.service;

import android.content.Context;

import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.PositionService;
import com.agh.gopr.app.service.rest.RequestService;
import com.agh.gopr.app.service.rest.service.method.RestMethod;
import com.google.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import roboguice.RoboGuice;
import roboguice.util.Ln;

public class GpsPostPositionsService implements IMethodService {

    @Inject
    private PositionService positionService;

    @Inject
    private Preferences_ preferences;

    @Inject
    private Context context;

    private final RequestService.HttpCallback callback = new PostCallback();

    @Inject
    public GpsPostPositionsService(Context context) {
        RoboGuice.injectMembers(context.getApplicationContext(), this);
    }

    @Override
    public void init() {
        handle();
    }

    @Override
    public void handle() {
        if (positionService.getNotPostedPositionsCount() == 0) {
            return;
        }
        List<Position> positions = positionService.getNotPostedPositions();

        try {
            Ln.d("Trying to post %d positions...", positions.size());
            JSONObject jsonObject = JsonHelper.createJsonFromPositions(preferences.userId().get(), positions);
            Ln.d("Posting JSON: " + jsonObject.toString().length());
            String actionId = preferences.actionId().get();
            try {
                RestMethod.POST_POINTS.run(context, callback, actionId, jsonObject.toString());
            } catch (MethodException e) {
                Ln.e("Could not post points: %s", e.getMessage());
            }

            positions.clear();
        } catch (JSONException e) {
            Ln.e("Could not create JSON");
        }
    }

    private class PostCallback implements RequestService.HttpCallback {

        @Override
        public synchronized void handle(String json) {
            Position position = positionService.getMostRecentPosition();
            preferences.lastPositionPostTime().put(position.getDate().getTime());
        }

        @Override
        public void onError(Throwable error) {
            Ln.e("Could not post positions: " + error.getMessage());
        }
    }
}