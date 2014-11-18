package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.rest.RequestService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class PostPointsMethod extends AbstractMethod {

    private static final String URL = "/api/point/post?";

    @Override
    public void run(RequestService.HttpCallback handler, String... params) throws MethodException {
        requestService.post(createUrl(params), handler);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 2)
            throw new MethodException.WrongNumberOfParametersException
                    ("Post points takes exactly two argument");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        pairs.add(new BasicNameValuePair(JsonHelper.POSITIONS_FIELD, params[1]));
        return pairs;
    }

    protected String createUrl(String... params) throws MethodException {
        return MethodHelper.createUrlWithActionId(this, params);
    }
}
