package com.airnauts.kaktus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.airnauts.toolkit.utils.TextUtils;


public class NotificationActivity extends Activity {

    public static final String EXTRA_NOTIFICATION_DATA = "notification_data";

//    public static Intent getAsIntent(Context context, NotificationData data) {
//        Intent intent = new Intent(context, NotificationActivity.class);
//        if (data != null) {
//            intent.putExtra(EXTRA_NOTIFICATION_DATA, data);
//        }
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String transactionId = null;
//        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRA_NOTIFICATION_DATA)) {
//            NotificationData data = (NotificationData) getIntent().getExtras().getSerializable(EXTRA_NOTIFICATION_DATA);
//            if (data != null && TextUtils.isNotEmpty(data.t)) {
//                transactionId = data.t;
//            }
//        }

//        if (!HomeActivity.sIsRunning) {
//            if (TextUtils.isNotEmpty(transactionId)) {
//                SplashActivity.startActivity(this, transactionId);
//            } else {
//                SplashActivity.startActivity(this);
//            }
//        } else if (!HomeActivity.sIsOnTop) {
//            if (TextUtils.isNotEmpty(transactionId)) {
//                HomeActivity.startActivity(this, transactionId);
//            }
//        } else {
//            final PocketPayApplication app = (PocketPayApplication) getApplication();
//            if (app.hasCurrentHomeActivity() && TextUtils.isNotEmpty(transactionId)) {
//                final HomeActivity currentHomeActivity = app.getCurrentHomeActivity();
//                currentHomeActivity.focusAtTransaction(transactionId);
//            }
//        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
