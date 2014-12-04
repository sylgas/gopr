package com.agh.gopr.app.ui.activity;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import roboguice.activity.RoboFragmentActivity;

@EActivity
public class AbstractActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar();
    }

}
