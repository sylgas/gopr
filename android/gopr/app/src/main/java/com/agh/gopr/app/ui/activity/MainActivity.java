package com.agh.gopr.app.ui.activity;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.agh.gopr.app.ui.fragment.MapFragment;
import com.agh.gopr.app.ui.fragment.MapFragment_;
import com.agh.gopr.app.ui.fragment.MessengerFragment_;
import com.agh.gopr.app.R;
import com.agh.gopr.app.ui.view.CustomViewPager;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import roboguice.event.Observes;

@EActivity(R.layout.main_activity)
public class MainActivity extends AbstractActivity {
    private static final String TAG = "MainActivity";

    private final SectionsPagerAdapter adapter;

    @ViewById
    protected CustomViewPager pager;

    public MainActivity() {
        this.adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    }

    @AfterViews
    protected void init() {
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    pager.setPagingEnabled(false);
                } else {
                    pager.setPagingEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OptionsItem(R.id.settings)
    protected void settings() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void disablePager( @Observes MapFragment.StartMessengerEvent startMessengerEvent) {
        Log.d(TAG, "Start messenger event");
        pager.setCurrentItem(1);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * is called to initiate the fragment for given page
         */
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return MapFragment_.builder().build();
            }
            return MessengerFragment_.builder().build();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
