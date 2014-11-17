package com.agh.gopr.app.common;

import android.app.AlertDialog;
import android.content.Context;

import com.agh.gopr.app.GoprTestRunner;
import com.agh.gopr.app.R;
import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowAlertDialog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(GoprTestRunner.class)
public class SettingsAlertDialogTest {

    @Inject
    private Context context;

    private SettingsAlertDialog dialog;

    @Before
    public void setUp() throws Exception {
        dialog = new SettingsAlertDialog(context);
    }

    @Test
    public void testShowingGpsDialog() throws Exception {
        assertDialog(SettingsAlertDialog.Setting.GPS);
    }

    @Test
    public void testShowingNetworkDialog() throws Exception {
        assertDialog(SettingsAlertDialog.Setting.NETWORK);
    }

    private void assertDialog(SettingsAlertDialog.Setting type) {
        dialog.showSettingsAlert(type);
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(alertDialog);
        String title = String.format(context.getString(R.string.not_enabled), type.getName());
        assertEquals(title, shadowOf(alertDialog).getTitle());
        assertEquals(context.getString(R.string.ask_for_settings), shadowOf(alertDialog).getMessage());
    }
}