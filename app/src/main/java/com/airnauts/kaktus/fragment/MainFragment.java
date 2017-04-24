package com.airnauts.kaktus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.model.facebook.Album;
import com.airnauts.kaktus.model.facebook.Photo;
import com.airnauts.kaktus.provider.AuthProvider;
import com.airnauts.kaktus.provider.listener.AuthProviderListener;
import com.airnauts.kaktus.view.ViewPagerIndicator;
import com.airnauts.kaktus.view.transformer.RotationalPageTransformer;
import com.airnauts.toolkit.adapter.ViewPagerPushNavigationAdapter;
import com.airnauts.toolkit.config.PushNavigationFragmentConfig;
import com.airnauts.toolkit.config.ViewPagerPushNavigationFragmentConfig;
import com.airnauts.toolkit.push.PushNavigationFragment;
import com.airnauts.toolkit.push.ViewPagerPushNavigationFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mradziko on 10.11.2015.
 */
public class MainFragment extends ViewPagerPushNavigationFragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public ViewPagerPushNavigationFragmentConfig initViewPagerConfig(Context context) {
        return new ViewPagerPushNavigationFragmentConfig.Builder("").hasEvenlyDistributedTabs().build();
    }

    @Override
    public ViewPagerPushNavigationAdapter getAdapter() {
        return new PagerAdapter(getApplicationContext(), getFragmentManager());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attach(AuthProvider.class, new AuthProviderListener() {
            @Override
            public void authenticationStateChanged(FirebaseUser user) {
                Toast.makeText(getApplicationContext(), "User " + user, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class PagerAdapter extends ViewPagerPushNavigationAdapter {

        public PagerAdapter(Context context, FragmentManager fm) {
            super(context, fm);
        }

        @Override
        public PushNavigationFragment initFragmentAt(int i) {
            return ShopsFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}