package com.agh.gopr.app.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.RequestService;

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
        if (params.length != 1)
            throw new MethodException.WrongNumberOfParametersException
                    ("Get points takes exactly two arguments");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        pairs.add(new BasicNameValuePair("dateTime", params[0]));
        return pairs;
    }
}
