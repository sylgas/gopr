package com.agh.gopr.app.common;

public class PreferenceHelper {
    protected static final String DEFAULT_ADDRESS = "http://192.168.43.53:8090";
    protected static final String DEFAULT_SITE = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";

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
}
