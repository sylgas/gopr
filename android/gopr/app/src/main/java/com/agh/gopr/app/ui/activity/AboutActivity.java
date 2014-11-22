package com.agh.gopr.app.ui.activity;

import android.content.Intent;
import android.net.Uri;

import com.agh.gopr.app.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.about_activity)
public class AboutActivity extends AbstractActivity {

    @Click(R.id.icons_info)
    protected void openIconSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://graphicriver.net/item/pictype-vector-icons/3917143?ref=tmthymllr"));
        startActivity(browserIntent);
    }
}
