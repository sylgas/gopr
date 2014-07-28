package com.agh.gopr.app.method;

import android.content.Context;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.RequestService;
import com.google.inject.Inject;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

public abstract class AbstractMethod {

    @Inject
    public RequestService requestService;

    public void init(Context context) {
        RoboGuice.injectMembers(context, this);
        requestService.init(context);
    }

    public abstract void run(RequestService.HttpCallback callback, String actionId, String... params)
            throws MethodException;

    protected abstract void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException;

    protected abstract String getMethodUrl();

    protected abstract List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params);

    protected String createUrl(String actionId, String... params) throws MethodException {
        if (actionId == null || actionId.isEmpty())
            throw new MethodException.EmptyMethodDefinitionException
                    ("ActionId cannot be empty");

        checkActionId(actionId);
        checkArguments(params);

        String methodUrl = getMethodUrl();
        String parsedParameters = buildParams(appendParameters(createParams(actionId), params));

        return String.format("%s%s", methodUrl, parsedParameters);
    }

    private String buildParams(List<NameValuePair> pairs) {
        return URLEncodedUtils.format(pairs, "utf-8");
    }

    protected boolean isNumeric(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void checkActionId(String actionId) throws MethodException.WrongParameterException {
        if (!isNumeric(actionId)) {
            throw new MethodException.WrongParameterException("Action Id must be integer");
        }
    }

    private List<NameValuePair> createParams(String actionId) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("actionId", actionId));
        return nameValuePairs;
    }
}
