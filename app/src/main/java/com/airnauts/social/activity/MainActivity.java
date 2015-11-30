package com.airnauts.social.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.airnauts.social.R;
import com.airnauts.social.utils.FacebookUtils;
import com.airnauts.social.view.ParallaxImageViewPager;
import com.airnauts.social.view.ViewPagerIndicator;
import com.airnauts.social.view.transformer.RotationalPageTransformer;
import com.airnauts.toolkit.api.Obtainable;
import com.airnauts.toolkit.api.parse.callback.LogInParseCallback;
import com.airnauts.toolkit.config.PushNavigationActivityConfig;
import com.facebook.GraphRequestBatch;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mradziko on 10.11.2015.
 */
public class MainActivity extends BaseSocialActivity {

    ArrayList<String> PERMISSIONS = new ArrayList() {{
        add("user_photos");
        add("user_friends");
        add("user_birthday");
    }};

    @InjectView(R.id.progress)
    ProgressBar mProgress;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.layout_login)
    RelativeLayout mLoginLayout;
    @InjectView(R.id.button_login)
    ImageButton mLoginButton;
    @InjectView(R.id.view_pager)
    ParallaxImageViewPager mViewPager;
    @InjectView(R.id.indicator)
    ViewPagerIndicator mIndicator;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected PushNavigationActivityConfig getConfig() {
        return PushNavigationActivityConfig.with(mToolbar, R.id.container, R.drawable.com_facebook_button_icon, R.drawable.com_facebook_button_icon).build();
    }

    @Override
    protected void onInternalCreate(Bundle bundle) {
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        final int[] resources = new int[]{R.layout.layout_tutorial_0, R.layout.layout_tutorial_1, R.layout.layout_tutorial_2, R.layout.layout_tutorial_3};
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, resources);

        mIndicator.setCount(resources.length);
        mIndicator.setSelection(0);
        mIndicator.animateIndicators();
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(false, new RotationalPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v("tag", position + " " + positionOffset + " " + positionOffsetPixels);
                synchronized (MainActivity.this) {

                    if (position == resources.length - 2) {
                        float valueY = new AccelerateInterpolator(1.3f).getInterpolation(positionOffset);
                        mLoginLayout.setTranslationY(valueY * (-mLoginLayout.getTop() + mViewPager.getTop() + mLoginLayout.getHeight()));
                        float valueX = new DecelerateInterpolator(2.1f).getInterpolation(positionOffset);
                        mLoginLayout.setTranslationX(valueX * (-mLoginLayout.getLeft() + mViewPager.getWidth() / 2 - mLoginLayout.getWidth() / 2));
                    } else if (position == resources.length - 1) {
                        mLoginLayout.setTranslationY(-mLoginLayout.getTop() + mViewPager.getTop() + mLoginLayout.getHeight());
                        mLoginLayout.setTranslationX(-mLoginLayout.getLeft() + mViewPager.getWidth() / 2 - mLoginLayout.getWidth() / 2);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityCreated(Bundle bundle) {
        setCenteredLogo(R.layout.layout_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    //Butter Knife
    @OnClick(R.id.button_login)
    void onLoginClick() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, PERMISSIONS, new LogInParseCallback(this) {
            @Override
            public void onSuccess(Obtainable obtainable, ParseUser parseUser) {
                GraphRequestBatch.Callback callback = new GraphRequestBatch.Callback() {
                    @Override
                    public void onBatchCompleted(GraphRequestBatch batch) {
                        startNextActivity();
                        mProgress.setVisibility(View.GONE);
                        mLoginButton.setEnabled(true);
                    }
                };

                if (parseUser == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (parseUser.isNew()) {
                    mProgress.setVisibility(View.VISIBLE);
                    mLoginButton.setEnabled(false);
                    FacebookUtils.updateFacebookData(callback);
                } else {
                    mProgress.setVisibility(View.VISIBLE);
                    mLoginButton.setEnabled(false);
                    FacebookUtils.updateFacebookData(callback);
                }
            }

            @Override
            public boolean onFailure(com.parse.ParseException e) {
                return false;
            }
        });
    }


    private void startNextActivity() {
        UpdateUserActivity.startActivity(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private int[] mResources;
        private Context mContext;

        public ViewPagerAdapter(Context context, int[] resources) {
            mContext = context;
            mResources = resources;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewPager pager = (ViewPager) container;
            View view = LayoutInflater.from(mContext).inflate(mResources[position], container, false);

            pager.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return mResources != null ? mResources.length : 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            ((ViewPager) container).removeView((View) view);
        }
    }
}