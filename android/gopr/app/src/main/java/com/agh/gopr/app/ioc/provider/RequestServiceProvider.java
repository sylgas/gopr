package com.agh.gopr.app.ioc.provider;

import android.content.Context;

import com.agh.gopr.app.service.RequestService_;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RequestServiceProvider implements Provider<RequestService_> {

    @Inject
    private Context context;

    @Override
    public RequestService_ get() {
        return RequestService_.getInstance_(context);
    }
}
