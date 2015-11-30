package com.airnauts.social.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airnauts.social.R;
import com.airnauts.toolkit.activity.ObtainableActivity;

/**
 * Created by mradziko on 10.11.2015.
 */
public class SplashActivity extends ObtainableActivity {

    private static final long SPLASH_DELAY = 1300;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SplashActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.startActivity(SplashActivity.this);
            }
        }, SPLASH_DELAY);

    }
}