package com.airnauts.social.application;

import android.app.Application;

import com.airnauts.social.R;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mradziko on 10.11.2015.
 */
public class SocialApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "QtKTIFISku4sLZXUGQGqH8ptkz85ZoE6FSCJAeTL", "i7YNLlndoXK2EohSKBYIKVYg2dqJjxf9NLmUXzDV");

        ParseFacebookUtils.initialize(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(getString(R.string.light)).setFontAttrId(R.attr.fontPath).build());
    }
}
