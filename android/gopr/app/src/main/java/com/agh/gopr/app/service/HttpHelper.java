package com.agh.gopr.app.service;

import com.agh.gopr.app.exception.GOPRException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpHelper {

    public static String createUrl(RestMethod type, String baseUrl, String... params) {
        if (baseUrl == null || params == null)
            return null;

        StringBuilder returnStringBuilder = createMethodUrl(type, baseUrl);

        List<NameValuePair> methodParams;
        switch (type) {
            case GetLayer:
                methodParams = createGetLayerParams(params);
                break;
            case GetPoints:
                methodParams = createPostPointsParams(params);
                break;
            case GetAllPoints:
                methodParams = createGetAllPointsParams(params);
                break;
            case SendPoints:
                methodParams = createPostPointsParams(params);
                break;
            default:
                //albo nulla zwrocic, nie wiem co lepiej  :x
                throw new GOPRException("No such rest method!");
        }

        returnStringBuilder.append(URLEncodedUtils.format(methodParams, "utf-8"));
        return returnStringBuilder.toString();
    }

    public static String getMethodType(RestMethod restMethod) {
        switch (restMethod) {
            case SendPoints:
                return "POST";
            case GetLayer:
            case GetPoints:
            case GetAllPoints:
                return "GET";
            default:
                throw new GOPRException("No such rest method!");
        }
    }

    private static StringBuilder createMethodUrl(RestMethod type, String baseUrl) {

        if (baseUrl == null)
            return null;

        StringBuilder returnStringBuilder = new StringBuilder(baseUrl);

        switch (type) {
            case GetLayer:
                return returnStringBuilder.append("/rest/layer/get?");
            case GetPoints:
                return returnStringBuilder.append("/rest/point/get?");
            case GetAllPoints:
                return returnStringBuilder.append("/rest/point/getAllPoints?");
            case SendPoints:
                return returnStringBuilder.append("/rest/point/send?");
            default:
                throw new GOPRException("No such rest method!");
        }
    }

    //region private helpers

    private static List<NameValuePair> createGetLayerParams(String... params) {

        if (params.length < 1)
            return null;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

        nameValuePairs.add(new BasicNameValuePair("actionId",
                params[0]));

        return nameValuePairs;
    }

    //region helpers for creating NameValuePair for each rest method

    private static List<NameValuePair> createGetPointsParams(String... params) {

        if (params.length < 2)
            return null;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        nameValuePairs.add(new BasicNameValuePair("actionId",
                params[0]));
        nameValuePairs.add(new BasicNameValuePair("dateTime",
                params[1]));

        return nameValuePairs;
    }

    private static List<NameValuePair> createGetAllPointsParams(String... params) {

        if (params.length < 1)
            return null;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

        nameValuePairs.add(new BasicNameValuePair("actionId",
                params[0]));

        return nameValuePairs;
    }

    private static List<NameValuePair> createPostPointsParams(String... params) {

        if (params.length < 2)
            return null;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

        nameValuePairs.add(new BasicNameValuePair("actionId",
                params[0]));

        nameValuePairs.add(new BasicNameValuePair("positions",
                params[1]));

        return nameValuePairs;
    }

    public enum RestMethod {
        GetLayer,
        GetPoints,
        GetAllPoints,
        SendPoints
    }

    //endregion

    //endregion
}