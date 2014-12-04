package com.agh.gopr.app;

import android.app.Application;
import android.os.Environment;

import com.agh.gopr.app.ioc.GOPRMobileModule;

import org.androidannotations.annotations.EApplication;

import java.io.File;

import roboguice.RoboGuice;

@EApplication
public class GOPRMobile extends Application {
    private static final String APP_FOLDER_NAME = "GeoSUP";
    private static final File APP_DIRECTORY = initializeApplicationDirectory();

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(this), new GOPRMobileModule());
    }

    public static File getAppDirectory() {
        return APP_DIRECTORY;
    }

    private static File initializeApplicationDirectory() {
        File appDirectory = new File(Environment.getExternalStorageDirectory(), APP_FOLDER_NAME);
        if (!appDirectory.exists()) {
            appDirectory.mkdir();
        }
        return appDirectory;
    }
}
