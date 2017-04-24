package com.airnauts.kaktus.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.airnauts.kaktus.R;
import com.airnauts.toolkit.config.PushNavigationActivityConfig;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mradziko on 10.11.2015.
 */
public class SplashActivity extends BaseActivity {

    private static final long SPLASH_DELAY = 1000;

    @Bind(R.id.image_splash)
    ImageView mSplashImage;

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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyObjectAnimator
                        .animate(mSplashImage)
                        .withLayer()
                        .alpha(1f)
                        .translationY(-40f)
                        .setDuration(300)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                startMainActivityWithDelay(SPLASH_DELAY);

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        })
                        .get()
                        .start();

                if (AccessToken.getCurrentAccessToken() == null) {
//                    ViewPropertyObjectAnimator
//                            .animate(mLoginButton)
//                            .withLayer()
//                            .alpha(1f)
//                            .translationY(0f)
//                            .setDuration(300)
//                            .setStartDelay(200)
//                            .setInterpolator(new AccelerateDecelerateInterpolator())
//                            .get()
//                            .start();
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