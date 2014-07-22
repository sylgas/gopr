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

    private HttpURLConnection httpURLConnection;

    public void init(Context context) {
        RoboGuice.injectMembers(context, this);
    }

    @Background
    public void get(String methodName, HttpCallback handler) {
        if (!connectivityService.enableConnection()) {
            return;
        }
        try {
            connect(buildUrl(methodName), "GET");
            String json = readFrom(httpURLConnection.getInputStream());
            handler.handle(json);
        } catch (Exception e) {
            handler.onError(e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    @Background
    public void post(String methodName, HttpCallback handler) {
        if (!connectivityService.enableConnection()) {
            return;
        }
        try {
            String url = buildUrl(methodName);
            connect(url, "POST");
            handler.handle(null);
        } catch (Exception e) {
            handler.onError(e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
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

    private String buildUrl(String methodName) {
        return String.format("%s%s", getBaseUrl(), methodName);
    }

    private String getBaseUrl() {
        return PreferenceHelper.getServerAddress(preferences);
    }

    private void connect(String methodUrl, String methodType) throws IOException, ConnectionException {
        Log.d(TAG, String.format("Connecting to %s", methodUrl));

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

    public interface HttpCallback {
        void handle(String json);

        void onError(Throwable error);
    }

    public class ConnectionException extends GOPRException {
        public ConnectionException(String detailMessage) {
            super(detailMessage);
        }
    }

}