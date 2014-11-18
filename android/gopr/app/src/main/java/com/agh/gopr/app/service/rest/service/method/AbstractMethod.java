package com.agh.gopr.app.service.rest.service.method;

import android.content.Context;

import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.map.JsonHelper;
import com.agh.gopr.app.service.rest.RequestService;
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

    public abstract void run(RequestService.HttpCallback callback, String... params)
            throws MethodException;

    protected abstract void checkArguments(String... params) throws MethodException.WrongNumberOfParametersException;

    protected abstract String getMethodUrl();

    protected abstract List<NameValuePair> appendParameters(List<NameValuePair> pairs, String... params);

    protected String buildParams(List<NameValuePair> pairs) {
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

    protected void checkActionId(String actionId) throws MethodException.WrongParameterException {
        if (!isNumeric(actionId)) {
            throw new MethodException.WrongParameterException("Action Id must be integer");
        }
    }

    protected List<NameValuePair> createParams(String actionId) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair(JsonHelper.ACTION_ID_FIELD, actionId));
        return nameValuePairs;
    }
}
