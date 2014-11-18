package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.rest.RequestService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class LoginMethod extends AbstractMethod {

    private static final String URL = "/api/user/login?";

    @Override
    public void run(RequestService.HttpCallback handler, String... params) throws MethodException {
        requestService.get(createUrl(params), handler);
    }

    @Override
    protected void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException {
        if (params.length != 2)
            throw new MethodException.WrongNumberOfParametersException
                    ("User login_menu takes exactly two arguments");
    }

    @Override
    protected String getMethodUrl() {
        return URL;
    }

    @Override
    protected List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params) {
        pairs.add(new BasicNameValuePair(JsonHelper.LOGIN, params[0]));
        pairs.add(new BasicNameValuePair(JsonHelper.PASSWORD, params[1]));
        return pairs;
    }

    protected String createUrl(String... params) throws MethodException {
        return MethodHelper.createUrl(this, params);
    }
}
