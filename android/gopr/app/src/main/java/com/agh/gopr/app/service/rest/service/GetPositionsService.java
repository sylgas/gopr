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

import roboguice.RoboGuice;
import roboguice.util.Ln;

public class GetPositionsService implements IMethodService {

    @Inject
    private Preferences_ preferences;

    @Inject
    private PositionService positionService;

    private final Context context;

    private final RequestService.HttpCallback callback = new GetCallback();

    private final UserPositionProcessListener listener = new UserPositionProcessListener();

    @Inject
    public GetPositionsService(Context context) {
        RoboGuice.injectMembers(context, this);
        this.context = context;
    }

    @Override
    public void init() {
        try {
            RestMethod.GET_ALL_POINTS.run(context, callback, preferences.actionId().get());
        } catch (MethodException e) {
            Ln.e("Could not get positions: %s", e.getMessage());
        }
    }

    @Override
    public void handle() {
        try {
            RestMethod.GET_POINTS.run(context, callback,
                    preferences.actionId().get(), String.valueOf(System.currentTimeMillis()));
        } catch (MethodException e) {
            Ln.e("Could not get positions: %s", e.getMessage());
        }
    }


    private class GetCallback implements RequestService.HttpCallback {

        @Override
        public void handle(String json) {
            JsonHelper.readUserPositions(json, listener);
        }

        @Override
        public void onError(Throwable error) {
            Ln.e("Could not get positions: %s", error.getMessage());
        }
    }

    private class UserPositionProcessListener implements JsonHelper.UserPositionProcessListener {

        @Override
        public void process(Position position) {
            positionService.save(position);
        }
    }
}
