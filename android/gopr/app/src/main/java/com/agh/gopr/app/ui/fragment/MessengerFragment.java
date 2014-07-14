package com.agh.gopr.app.ui.fragment;

import android.widget.ListView;

import com.agh.gopr.app.R;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;

import roboguice.event.EventManager;
import roboguice.fragment.RoboFragment;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.messenger_fragment)
@OptionsMenu(R.menu.messager_menu)
public class MessengerFragment extends RoboFragment {

    @ViewById
    protected ListView list;

    @Inject
    protected EventManager manager;

    @AfterViews
    protected void init() {
    }

    @OptionsItem(R.id.create)
    protected void createMessage() {

    }

}