package com.agh.gopr.app.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.agh.gopr.app.R;
import com.google.inject.Inject;

public class SettingsAlertDialog {
    private final Context context;
    private final AlertDialog.Builder dialog;
    private final String settingNotEnabled;

    @Inject
    public SettingsAlertDialog(Context context) {
        this.context = context;
        settingNotEnabled = context.getString(R.string.not_enabled);
        dialog = buildDialog();
    }

    /**
     * Must be executed in UiThread
     * @param setting
     */
    public void showSettingsAlert(final Setting setting) {
        dialog.setTitle(String.format(settingNotEnabled, setting.getName()));
        dialog.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(setting.getSetting());
                context.startActivity(intent);
            }
        });
        dialog.show();
    }

    private AlertDialog.Builder buildDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(context.getString(R.string.ask_for_settings));
        alertDialog.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return alertDialog;
    }

    public enum Setting {
        GPS("GPS", Settings.ACTION_LOCATION_SOURCE_SETTINGS),
        NETWORK("Network", Settings.ACTION_SETTINGS);

        private String name;
        private String setting;

        Setting(String name, String setting) {
            this.name = name;
            this.setting = setting;
        }

        public String getName() {
            return name;
        }

        public String getSetting() {
            return setting;
        }
    }
}