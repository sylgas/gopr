package com.agh.gopr.app.common;

import com.googlecode.androidannotations.annotations.sharedpreferences.*;

@SuppressWarnings("UnusedDeclaration")
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    String serverAddress();

    String mapSite();

    boolean showLayer();

    String login();
}
