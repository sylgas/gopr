package com.agh.gopr.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.agh.gopr.app.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import roboguice.activity.RoboFragmentActivity;

@EActivity
@OptionsMenu(R.menu.menu)
public class AbstractActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar();
    }

    @OptionsItem(R.id.settings)
    protected void settings() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    @OptionsItem(R.id.about)
    protected void about() {
        AboutActivity_.intent(this).start();
    }

}
