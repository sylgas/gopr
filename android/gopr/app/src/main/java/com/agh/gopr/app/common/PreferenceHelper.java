package com.agh.gopr.app.common;

public class PreferenceHelper {
    protected static final String DEFAULT_ADDRESS = "http://192.168.43.53:8090";
    protected static final String DEFAULT_SITE = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
    protected static final String DEFAULT_CURRENT_ACTION_ID = "1";

    public static String getServerAddress(Preferences_ preferences) {
        String address = preferences.serverAddress().get();
        if (address == null || address.isEmpty()) {
            return DEFAULT_ADDRESS;
        }
        return address;
    }

    public static String getMapSite(Preferences_ preferences) {
        String address = preferences.mapSite().get();
        if (address == null || address.isEmpty()) {
            return DEFAULT_SITE;
        }
        return address;
    }

    public static String getCurrentActionId(Preferences_ preferences) {
        String currentActionId = preferences.actionId().get();
        if (currentActionId == null || currentActionId.isEmpty())
            return DEFAULT_CURRENT_ACTION_ID;
        return currentActionId;
    }
}
