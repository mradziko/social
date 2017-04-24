package com.airnauts.kaktus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airnauts.kaktus.R;
import com.airnauts.toolkit.config.PushNavigationFragmentConfig;
import com.airnauts.toolkit.push.PushNavigationFragment;

/**
 * Created by mradziko on 10.11.2015.
 */
public class ShopsFragment extends PushNavigationFragment {

    public static ShopsFragment newInstance() {
        ShopsFragment fragment = new ShopsFragment();
        return fragment;
    }

    @Override
    protected PushNavigationFragmentConfig initConfig(Context context) {
        return new PushNavigationFragmentConfig.Builder("SHOPS").build();
    }

    @Override
    public View onCreateFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_shops, viewGroup, false);
    }

}