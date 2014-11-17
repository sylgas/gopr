package com.agh.gopr.app.ui.fragment;

import android.widget.ListView;

import com.agh.gopr.app.R;

import com.google.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

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