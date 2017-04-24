package com.airnauts.kaktus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airnauts.kaktus.R;

/**
 * Created by mradziko on 20.10.2014.
 */
public class ViewPagerIndicator extends LinearLayout {

    public static final int INITIAL_ANIMATION_DELAY = 300;
    public static final int ANIMATION_DELAY = 50;
    public static final float ALPHA_REVEAL_START = 0.3f;
    public static final float ALPHA_START = 0f;
    public static final float ALPHA_END = 1f;
    public static final int TRANSLATE_Y = 8;
    public static final int ANIMATION_DURATION = 200;
    public static final int DRAG_ANIMATION_START = INITIAL_ANIMATION_DELAY + (6 * ANIMATION_DELAY) + 1000;

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context) {
        super(context, null, 0);
    }

    public int getCount(){
        return getChildCount();
    }

    public void setCount(int count) {
        removeAllViews();

        for (int i = 0; i < count; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_dot, this);
        }

        ((ImageView) getChildAt(0)).setImageResource(R.drawable.view_pager_indicator_selected);
    }

    public void setSelection(int selection) {
        if (selection < getChildCount()) {
            for (int i = 0; i < getChildCount(); i++) {
                ((ImageView) getChildAt(i)).setImageResource(i == selection ? R.drawable.view_pager_indicator_selected : R.drawable.view_pager_indicator);
            }
        }
    }

    public void animateIndicators() {
        DecelerateInterpolator quadraticDecelerateInterpolator = new DecelerateInterpolator(2);
        for (int i = 0 ; i < getChildCount(); i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            imageView.setAlpha(ALPHA_START);
            imageView.setTranslationY(-TRANSLATE_Y);
            imageView.animate().alpha(ALPHA_END).translationYBy(TRANSLATE_Y).setDuration(ANIMATION_DURATION).setStartDelay(DRAG_ANIMATION_START + ((i + 1) * ANIMATION_DELAY)).setInterpolator(quadraticDecelerateInterpolator).start();
        }
    }
}
