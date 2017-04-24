package com.airnauts.kaktus.application;

import android.app.Application;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.utils.LocationUtils;
import com.airnauts.toolkit.data.DataManager;
import com.facebook.FacebookSdk;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mradziko on 10.11.2015.
 */
public class KaktusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(getString(R.string.light)).setFontAttrId(R.attr.fontPath).build());

        LocationUtils.getInstance(getApplicationContext()).connect();

        DataManager.getInstance().initialize(getApplicationContext());
    }
}
