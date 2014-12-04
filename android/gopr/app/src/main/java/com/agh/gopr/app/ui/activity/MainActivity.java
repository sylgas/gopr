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
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import roboguice.event.Observes;

@EActivity(R.layout.main_activity)
@OptionsMenu(R.menu.menu)
public class MainActivity extends AbstractActivity {

    private final SectionsPagerAdapter adapter;

    @Inject
    private MapFragment mapFragment;

    /**
     * It needed to start posting position (service starts in constructor)
     */
    @Inject
    private GpsPostPositionsService postPositionsService;

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

    @OptionsItem(R.id.notes)
    protected void notes() {
        if (noteFragment.isVisible()) {
            noteFragment.hide();
        } else {
            noteFragment.show();
        }
    }

    @OptionsItem(R.id.settings)
    protected void settings() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    @OptionsItem(R.id.about)
    protected void about() {
        AboutActivity_.intent(this).start();
    }

    protected void disablePager(@Observes MapFragment.StartMessengerEvent startMessengerEvent) {
        pager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (noteFragment.isVisible()) {
            noteFragment.hide();
        } else if (pager.getCurrentItem() != SectionsPagerAdapter.MAP_FRAGMENT_INDEX) {
            pager.setCurrentItem(SectionsPagerAdapter.MAP_FRAGMENT_INDEX);
        } else {
            super.onBackPressed();
        }
    }

    private class ScrollPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position != SectionsPagerAdapter.MAP_FRAGMENT_INDEX) {
                if (noteFragment.isVisible()) {
                    noteFragment.hide();
                }
                pager.setPagingEnabled(true);
                getActionBar().setTitle(R.string.messenger_title);
            } else {
                pager.setPagingEnabled(false);
                getActionBar().setTitle(R.string.map_title);
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
        public static final int MAP_FRAGMENT_INDEX = 0;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * is called to initiate the fragment for given page
         */
        @Override
        public Fragment getItem(int position) {
            if (position == MAP_FRAGMENT_INDEX) {
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
