package com.agh.gopr.app.service.rest;

import com.agh.gopr.app.service.RequestService;
import com.agh.gopr.app.service.rest.exception.MethodException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by Sylwia on 2014-07-21.
 */
public class GetPointsMethod extends AbstractMethod {

    private static final String URL = "/rest/point/get?";

    @Override
    public void run(RequestService.HttpCallback handler, String actionId, String... params) throws MethodException {
        requestService.get(createUrl(actionId, params), handler);
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
        pairs.add(new BasicNameValuePair("actionId", params[0]));
        pairs.add(new BasicNameValuePair("dateTime", params[1]));
        return pairs;
    }
}
