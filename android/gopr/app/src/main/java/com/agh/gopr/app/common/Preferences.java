package com.agh.gopr.app.common;


import com.agh.gopr.app.R;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SuppressWarnings("UnusedDeclaration")
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    @DefaultRes(R.string.default_server_address)
    String serverAddress();

    @DefaultRes(R.string.default_map_site)
    String mapSite();

    String actionId();

    boolean showLayer();

    String userId();

    @DefaultLong(0L)
    long lastPositionPostTime();
}
