package com.agh.gopr.app.service.rest;

import android.content.Context;

import com.agh.gopr.app.service.RequestService;
import com.agh.gopr.app.service.rest.exception.MethodException;

public enum RestMethod {
    GET_LAYER(GetLayerMethod.class),
    GET_POINTS(GetPointsMethod.class),
    GET_ALL_POINTS(GetAllPointsMethod.class),
    POST_POINTS(PostPointsMethod.class);

    private final AbstractMethod method;

    private RestMethod(Class<? extends AbstractMethod> methodClass) {
        try {
            method = methodClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Method class is required to have no-arguments constructor.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Method class is required to have no-arguments constructor.");
        }
    }

    public void run(Context context, RequestService.HttpCallback callback, String actionId, String... params) throws MethodException {
        method.init(context);
        method.run(callback, actionId, params);
    }
}
