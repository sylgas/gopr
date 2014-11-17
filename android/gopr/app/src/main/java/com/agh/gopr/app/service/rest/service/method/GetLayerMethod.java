package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.rest.RequestService;

import org.apache.http.NameValuePair;

import java.util.List;

public class GetLayerMethod extends AbstractMethod {

    private static final String URL = "/area/get?";

    @Override
    public void run(RequestService.HttpCallback handler, String... params) throws MethodException {
        requestService.get(createUrl(params), handler);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 1)
            throw new MethodException.WrongNumberOfParametersException
                    ("Get layer takes exactly one argument");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        return pairs;
    }

    @Override
    protected String createUrl(String... params) throws MethodException {
        return MethodHelper.createUrlWithActionId(this, params);
    }
}
