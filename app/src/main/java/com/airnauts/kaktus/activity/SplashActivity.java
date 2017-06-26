package com.airnauts.kaktus.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.provider.AuthProvider;
import com.airnauts.kaktus.provider.listener.AuthProviderListener;
import com.airnauts.kaktus.utils.SplashUtils;
import com.airnauts.toolkit.config.PushNavigationActivityConfig;
import com.airnauts.toolkit.data.DataManager;
import com.airnauts.toolkit.push.DataProviderActivity;
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mradziko on 10.11.2015.
 */
public class SplashActivity extends BaseActivity {

    private static final long SPLASH_DELAY = 1000;

    @Bind(R.id.button_login)
    Button mLoginButton;
    @Bind(R.id.image_splash)
    ImageView mSplashImage;
    @Bind(R.id.layout_content)
    RelativeLayout mContentLayout;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SplashActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected PushNavigationActivityConfig initializeConfig() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        attach(AuthProvider.class, new AuthProviderListener() {
            @Override
            public void authenticationStateChanged(FirebaseUser user) {
                Toast.makeText(getApplicationContext(), "User " + user, Toast.LENGTH_LONG).show();
//                startMainActivityWithDelay(0);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.provide(AuthProvider.class).startAuth(SplashActivity.this);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (AccessToken.getCurrentAccessToken() == null) {
                    ViewPropertyObjectAnimator
                            .animate(mLoginButton)
                            .withLayer()
                            .alpha(1f)
                            .translationY(0f)
                            .setDuration(300)
                            .setStartDelay(200)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .get()
                            .start();
                }

            }
        }, SPLASH_DELAY);
    }


    private void startMainActivityWithDelay(long delay) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.startActivity(SplashActivity.this);
                finish();
            }
        }, delay);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}