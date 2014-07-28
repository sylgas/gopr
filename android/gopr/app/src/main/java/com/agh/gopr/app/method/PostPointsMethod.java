package com.agh.gopr.app.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.RequestService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class PostPointsMethod extends AbstractMethod {

    private static final String URL = "/rest/point/send?";

    @Override
    public void run(RequestService.HttpCallback handler, String actionId, String... params) throws MethodException {
        requestService.post(createUrl(actionId, params), handler);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 1)
            throw new MethodException.WrongNumberOfParametersException
                    ("Post points takes exactly one argument");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        pairs.add(new BasicNameValuePair("positions", params[0]));
        return pairs;
    }
}
