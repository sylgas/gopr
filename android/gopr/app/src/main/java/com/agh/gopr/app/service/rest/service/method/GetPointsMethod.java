package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.rest.RequestService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class GetPointsMethod extends AbstractMethod {

    private static final String URL = "/rest/point/get?";

    @Override
    public void run(RequestService.HttpCallback handler, String... params) throws MethodException {
        requestService.get(createUrl(params), handler);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 2)
            throw new MethodException.WrongNumberOfParametersException
                    ("Get points takes exactly two arguments");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        pairs.add(new BasicNameValuePair(JsonHelper.DATE_TIME_FIELD, params[1]));
        return pairs;
    }

    @Override
    protected String createUrl(String... params) throws MethodException {
        return MethodHelper.createUrlWithActionId(this, params);
    }
}
