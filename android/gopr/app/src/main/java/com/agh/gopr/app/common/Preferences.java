package com.agh.gopr.app.common;


import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SuppressWarnings("UnusedDeclaration")
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    String serverAddress();

    String mapSite();

    @DefaultString("1")
    String actionId();

    boolean showLayer();

    @DefaultString("sylwusiusiunia")
    String userId();

    @DefaultLong(0L)
    long lastPositionPostTime();
}
