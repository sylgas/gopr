package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.exception.MethodException;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class MethodHelper {

    static public String createUrlWithActionId(AbstractMethod method, String... params) throws MethodException {
        String actionId = params[0];
        if (actionId == null || actionId.isEmpty())
            throw new MethodException.EmptyMethodDefinitionException
                    ("ActionId cannot be empty");

        method.checkActionId(actionId);
        method.checkArguments(params);

        String methodUrl = method.getMethodUrl();
        String parsedParameters = method.buildParams(method.appendParameters(method.createParams(actionId), params));

        return String.format("%s%s", methodUrl, parsedParameters);
    }

    static public String createUrl(AbstractMethod method, String... params) throws MethodException {
        method.checkArguments(params);

        String methodUrl = method.getMethodUrl();
        String parsedParameters = method.buildParams(method.appendParameters(new ArrayList<NameValuePair>(), params));

        return String.format("%s%s", methodUrl, parsedParameters);
    }
}
