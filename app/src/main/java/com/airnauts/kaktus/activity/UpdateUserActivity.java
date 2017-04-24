package com.airnauts.kaktus.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airnauts.kaktus.utils.KeyboardUtils;
import com.airnauts.kaktus.utils.RegexUtils;
import com.airnauts.kaktus.R;
import com.airnauts.toolkit.config.PushNavigationActivityConfig;
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mradziko on 10.11.2015.
 */
public class UpdateUserActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_text)
    EditText mEditText;
    @Bind(R.id.button_save)
    Button mSaveButton;
    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.text_title)
    TextView mTitleText;
    private long mInitialLength, mDuration;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), UpdateUserActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected PushNavigationActivityConfig initializeConfig() {
        return PushNavigationActivityConfig.with(R.id.toolbar, R.layout.activity_update_user, R.id.container, R.drawable.com_facebook_button_icon, R.drawable.com_facebook_button_icon).build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mEditText.setSelection(mEditText.getText().length());
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mSaveButton.performClick();
                    return true;
                }
                return false;
            }
        });
        animateClear(1500, 1400);
        setCenteredLogo(R.layout.layout_logo);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.button_save)
    void onButtonClick() {
        if (mEditText.getText().length() == 0) {
            return;
        } else if (RegexUtils.isProperUsername(mEditText.getText().toString())) {
        } else {
            mTitleText.setText(getString(R.string.username_must_have_more_than));
            animateTitleColor(getResources().getColor(R.color.redTextColor));
        }
    }


    private void animateTitleColor(int color) {
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(mTitleText, "textColor", color);
        colorAnimator.setDuration(300);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.start();
    }

    private void animateButtonVisibility(boolean visible) {
        ObjectAnimator buttonAnimator = ViewPropertyObjectAnimator.animate(mSaveButton).withLayer().alpha(visible ? 1 : 0).translationY(visible ? 0 : mSaveButton.getHeight() / 2).setStartDelay(200).setDuration(200).setInterpolator(visible ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator()).get();
        buttonAnimator.start();
    }

    private void animateProgressVisibility(boolean visible) {
        ObjectAnimator buttonAnimator = ViewPropertyObjectAnimator.animate(mProgress).withLayer().alpha(visible ? 1 : 0).translationY(visible ? 0 : -mProgress.getHeight() / 2).setStartDelay(200).setDuration(200).setInterpolator(visible ? new FastOutLinearInInterpolator() : new LinearOutSlowInInterpolator()).get();
        buttonAnimator.start();
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mEditText.setText(mEditText.getText().toString().substring(0, mEditText.getText().toString().length() - 1));
            if (mEditText.getText().toString().length() == 0) {
                mEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() == 0 || s.length() == 1) {
                            animateButtonVisibility(s.length() > 0);
                        }
                        mTitleText.setText(R.string.setup_your_username_here);
                        mTitleText.setTextColor(getResources().getColor(R.color.darkTextColor));
                    }
                });
                KeyboardUtils.showKeyboard(UpdateUserActivity.this, mEditText);
                return;
            }
            mEditText.setSelection(mEditText.getText().length());
            DecelerateInterpolator interpolator = new DecelerateInterpolator(2);
            float interpolation = (interpolator.getInterpolation((mEditText.getText().length() + 1) / (float) mInitialLength)) - (interpolator.getInterpolation(mEditText.getText().length() / (float) mInitialLength));
            float delay = (mDuration / mInitialLength) * interpolation;
            long l = (long) delay;
            if (mEditText.getText().length() > 0) {
                mEditText.postDelayed(mRunnable, l);
            }
        }
    };

    public void animateClear(final long duration, long delay) {
        if (mEditText.getText().length() == 0) {
            return;
        }
        mDuration = duration;
        mInitialLength = mEditText.getText().length();
        mEditText.postDelayed(mRunnable, delay);
    }
}