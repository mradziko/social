package com.airnauts.social.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.airnauts.social.R;
import com.airnauts.toolkit.activity.ObtainableActivity;
import com.airnauts.toolkit.activity.PushNavigationActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mradziko on 10.11.2015.
 */
public abstract class BaseSocialActivity extends PushNavigationActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setCenteredLogo(int layout){
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View logo = LayoutInflater.from(this).inflate(layout, null);
        getSupportActionBar().setCustomView(logo, layoutParams);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}