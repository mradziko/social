package com.airnauts.kaktus.view.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by mradziko on 15.10.2014.
 */
public class RotationalPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(0);
        } else if (position <= 0) { // [-1,0]
            view.setPivotY(view.getHeight() / 2);
            view.setPivotX(0);
            view.setAlpha(1 + position);
            //view.setRotation(position * 10);
            view.setTranslationX(-position * view.getWidth() / 2);
        } else if (position <= 1) { // (0,1]
            view.setPivotY(view.getHeight() / 2);
            view.setPivotX(view.getWidth() / 2);
            //view.setRotation(position * 10);
        } else { // (1,+Infinity]
            view.setAlpha(1);
        }
    }
}