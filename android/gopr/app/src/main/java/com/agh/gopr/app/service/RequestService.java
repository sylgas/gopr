package com.agh.gopr.app.service;

import android.content.Context;
import android.util.Log;

import com.agh.gopr.app.common.PreferenceHelper;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.exception.GOPRException;
import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import roboguice.RoboGuice;

@EBean
public class RequestService {

    private static final String TAG = "RequestService";

    @Pref
    protected Preferences_ preferences;

    @Inject
    private ConnectivityService connectivityService;

    private JSONHandler handler;
    private HttpHelper.RestMethod restMethod;
    private HttpURLConnection httpURLConnection;
    private String methodType;

    public void init(Context context, JSONHandler handler, HttpHelper.RestMethod restMethod) {
        RoboGuice.injectMembers(context, this);
        this.handler = handler;
        this.restMethod = restMethod;
        this.methodType = HttpHelper.getMethodType(restMethod);
    }

    @Background
    public void sendUpdateRequest(String... params) {
        if (!connectivityService.enableConnection()) {
            return;
        }

        try {
            String url = HttpHelper.createUrl(restMethod,
                    PreferenceHelper.getServerAddress(preferences), params);
            connect(url);
            readJson();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private void readJson() throws IOException, JSONException {
        handler.handle(readFrom(httpURLConnection.getInputStream()), restMethod);
    }

    private String readFrom(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    private void connect(String methodUrl) throws IOException {
        Log.d(TAG, String.format("Getting layer from address: %s", methodUrl));

        URL url = new URL(methodUrl);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(methodType);
        httpURLConnection.setDoInput(true);

        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new ConnectionException(String.format("Could not connect to server, request code[%d]",
                    httpURLConnection.getResponseCode()));
        }
    }

    public interface JSONHandler {
        void handle(String json, HttpHelper.RestMethod restMethod);
    }

    private class ConnectionException extends GOPRException {
        public ConnectionException(String detailMessage) {
            super(detailMessage);
        }
    }

}