package com.airnauts.kaktus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.fragment.MainFragment;
import com.airnauts.kaktus.fragment.SelectionFragment;
import com.airnauts.kaktus.fragment.SingleSelectionFragment;
import com.airnauts.kaktus.view.NavigationButton;
import com.airnauts.toolkit.config.PushNavigationActivityConfig;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mradziko on 10.11.2015.
 */
public class MainActivity extends BaseActivity {

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected PushNavigationActivityConfig initializeConfig() {
        return PushNavigationActivityConfig.with(R.id.toolbar, R.layout.activity_main, R.id.container, R.drawable.com_facebook_button_icon, R.drawable.com_facebook_button_icon).build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setCenteredLogo(R.layout.layout_logo);
        pushNavigationFragment(MainFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}