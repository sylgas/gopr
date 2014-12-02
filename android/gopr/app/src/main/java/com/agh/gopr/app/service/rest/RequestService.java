package com.agh.gopr.app.service.rest;

import android.content.Context;
import android.widget.Toast;

import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.exception.GoprException;
import com.agh.gopr.app.service.connection.ConnectionService;
import com.google.inject.Inject;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import roboguice.RoboGuice;
import roboguice.util.Ln;

@EBean
public class RequestService {

    private static final int CONNECTION_TIMEOUT_IN_MILLIS = 5000;

    @Inject
    private ConnectionService connectionService;

    @Pref
    protected Preferences_ preferences;

    private HttpURLConnection httpURLConnection;

    private Context context;

    public void init(Context context) {
        this.context = context;
        RoboGuice.injectMembers(context, this);
    }

    @Background
    public void get(String methodName, HttpCallback handler) {
        if (!connectionService.isConnected()) {
            handler.onError(new ConnectionException("No Connection found"));
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
        if (!connectionService.isConnected()) {
            handler.onError(new ConnectionException("No Connection found"));
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
        return preferences.serverAddress().get();
    }

    private void connect(String methodUrl, String methodType) throws IOException, ConnectionException {
        Ln.d("Connecting to %s", methodUrl);

        URL url = new URL(methodUrl);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(methodType);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT_IN_MILLIS);

        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            toast(String.format("%d %s", httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage()));
            throw new ConnectionException(String.format("Could not connect to server\nrequest code[%d]\nrequest message[%s]",
                    httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage()));
        }
    }

    @UiThread
    protected void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public interface HttpCallback {
        void handle(String response);

        void onError(Throwable error);
    }

    public class ConnectionException extends GoprException {
        public ConnectionException(String detailMessage) {
            super(detailMessage);
        }
    }

}