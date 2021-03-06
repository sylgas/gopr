package com.agh.gopr.app.service.rest.service.method;

import android.content.Context;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.rest.RequestService;

public enum RestMethod {
    GET_LAYER(GetLayerMethod.class),
    GET_POINTS(GetPointsMethod.class),
    GET_ALL_POINTS(GetAllPointsMethod.class),
    POST_POINTS(PostPointsMethod.class),
    LOGIN(LoginMethod.class);

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

    public void run(Context context, RequestService.HttpCallback callback, String... params) throws MethodException {
        method.init(context);
        method.run(callback, params);
    }
}
