package com.agh.gopr.app.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.RequestService;

import org.apache.http.NameValuePair;

import java.util.List;

public class GetAllPointsMethod extends AbstractMethod {

    private static final String URL = "/rest/point/getAllPoints?";

    @Override
    public void run(RequestService.HttpCallback callback, String actionId, String... params)
            throws MethodException {
        requestService.get(createUrl(actionId, params), callback);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 0)
            throw new MethodException.WrongNumberOfParametersException("Get all points takes exactly one argument");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        return pairs;
    }
}
