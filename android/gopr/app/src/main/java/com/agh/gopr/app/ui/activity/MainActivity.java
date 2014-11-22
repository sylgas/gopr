package com.agh.gopr.app.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.agh.gopr.app.R;
import com.agh.gopr.app.service.rest.service.GpsPostPositionsService;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.agh.gopr.app.ui.fragment.MessengerFragment_;
import com.agh.gopr.app.ui.fragment.NoteFragment;
import com.agh.gopr.app.ui.view.CustomViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import roboguice.event.Observes;

@EActivity(R.layout.main_activity)
public class MainActivity extends AbstractActivity {

    private final SectionsPagerAdapter adapter;

    private GpsPostPositionsService gpsPostPositions;

    @Inject
    private MapFragment mapFragment;

    @Inject
    private GpsPostPositionsService getGpsPostPositions;

    @FragmentById
    protected NoteFragment noteFragment;


    @ViewById
    protected CustomViewPager pager;

    public MainActivity() {
        this.adapter = new SectionsPagerAdapter(getSupportFragmentManager());
    }

    @AfterViews
    protected void init() {
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ScrollPageListener());
    }

    @OptionsItem(R.id.settings)
    protected void settings() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    @OptionsItem(R.id.notes)
    protected void notes() {
        if (noteFragment.isVisible())
            noteFragment.hide();
        else
            noteFragment.show();
    }

    protected void disablePager(@Observes MapFragment.StartMessengerEvent startMessengerEvent) {
        pager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (noteFragment.isVisible())
            noteFragment.hide();
    }

    private class ScrollPageListener implements ViewPager.OnPageChangeListener {
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
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * is called to initiate the fragment for given page
         */
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mapFragment;
            }
            return MessengerFragment_.builder().build();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
