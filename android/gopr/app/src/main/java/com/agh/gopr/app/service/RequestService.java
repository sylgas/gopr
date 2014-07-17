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
    private HttpURLConnection httpURLConnection;

    public void init(Context context, JSONHandler handler) {
        RoboGuice.injectMembers(context, this);
        this.handler = handler;
    }

    @Background
    public void sendUpdateRequest(String nameMethod, String methodType) {
        if (!connectivityService.enableConnection()) {
            return;
        }

        try {
            connect(nameMethod, methodType);
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
        handler.handle(readFrom(httpURLConnection.getInputStream()));
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

    private void connect(String nameMethod, String methodType) throws IOException {
        //TODO: jakos poprawic
        String address = PreferenceHelper.getServerAddress(preferences);
        address += nameMethod;
        Log.d(TAG, String.format("Getting layer from address: %s", address));

        URL url = new URL(address);
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
        void handle(String json);
    }

    private class ConnectionException extends GOPRException {
        public ConnectionException(String detailMessage) {
            super(detailMessage);
        }
    }

}