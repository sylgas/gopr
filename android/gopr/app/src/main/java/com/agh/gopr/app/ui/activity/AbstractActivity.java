package com.agh.gopr.app.ui.activity;

import android.os.Bundle;

import roboguice.activity.RoboFragmentActivity;

public class AbstractActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar();
    }
}
