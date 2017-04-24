package com.airnauts.kaktus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.kaelaela.verticalviewpager.VerticalViewPager;

/**
 * Created by mradziko on 20.10.2014.
 */
public class CustomVerticalViewPager extends VerticalViewPager {

    public CustomVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVerticalViewPager(Context context) {
        super(context, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
