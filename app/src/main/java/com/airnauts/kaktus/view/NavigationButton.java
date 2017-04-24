package com.airnauts.kaktus.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airnauts.kaktus.utils.MeasureUtils;
import com.airnauts.kaktus.utils.VibrationUtils;
import com.airnauts.kaktus.R;
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnTouch;

/**
 * Created by mradziko on 20.10.2014.
 */
public class NavigationButton extends RelativeLayout {

    public enum Navigation {
        IMAGE, INTEREST, LOCATION;
    }

    public interface NavigationSelectedListener {
        public void onNavigationSelected(Navigation navigation);
    }

    private float WIDTH = 50, HEIGHT = 50;
    private float EXTENDED_WIDTH = 300, EXTENDED_HEIGHT = 300;
    private float OFFSET = 0.1f;
    private NavigationSelectedListener mListener;

    @Bind(R.id.layout_main)
    RelativeLayout mMainLayout;
    @Bind(R.id.button_picture)
    ImageView mPictureButton;
    @Bind(R.id.button_location)
    ImageView mLocationButton;
    @Bind(R.id.button_interests)
    ImageView mInterestButton;
    @Bind(R.id.layout_picture)
    RelativeLayout mPictureLayout;
    @Bind(R.id.layout_location)
    RelativeLayout mLocationLayout;
    @Bind(R.id.layout_interests)
    RelativeLayout mInterestLayout;
    @Bind(R.id.view_picture)
    View mPictureView;
    @Bind(R.id.view_location)
    View mLocationView;
    @Bind(R.id.view_interests)
    View mInterestsView;

    public NavigationButton(Context context) {
        super(context);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_navigation_button, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setNavigationSelectedListener(NavigationSelectedListener listener) {
        mListener = listener;
    }

    @OnTouch(R.id.layout_main)
    boolean onTouched(MotionEvent event) {
        int width = (int) MeasureUtils.getPx(getContext(), WIDTH);
        int height = (int) MeasureUtils.getPx(getContext(), HEIGHT);
        int extendedWidth = (int) MeasureUtils.getPx(getContext(), EXTENDED_WIDTH);
        int extendedHeight = (int) MeasureUtils.getPx(getContext(), EXTENDED_HEIGHT);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            animate(mMainLayout, extendedWidth, extendedHeight, (extendedWidth - width) / 2, (extendedHeight - height) / 2, 200, 1);

            Translation interestTranslation = getButtonTranslation(extendedWidth, extendedHeight, width, height, 0, 0, 1, 1);
            animate(mInterestLayout, width, height, interestTranslation.getX(), interestTranslation.getY(), 200, 1);
            Translation pictureTranslation = getButtonTranslation(extendedWidth, extendedHeight, width, height, 0, 0, 1, -OFFSET);
            animate(mPictureLayout, width, height, pictureTranslation.getX(), pictureTranslation.getY(), 200, 1);
            Translation locationTranslation = getButtonTranslation(extendedWidth, extendedHeight, width, height, 0, 0, -OFFSET, 1);
            animate(mLocationLayout, width, height, locationTranslation.getX(), locationTranslation.getY(), 200, 1);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mListener != null && mMainLayout.getWidth() == extendedWidth && mMainLayout.getHeight() == extendedHeight) {
                if (getDistanceFromCenter(mInterestLayout, event.getX(), event.getY()) < width) {
                    mListener.onNavigationSelected(Navigation.IMAGE);
                }
                if (getDistanceFromCenter(mPictureLayout, event.getX(), event.getY()) < width) {
                    mListener.onNavigationSelected(Navigation.INTEREST);
                }
                if (getDistanceFromCenter(mLocationLayout, event.getX(), event.getY()) < width) {
                    mListener.onNavigationSelected(Navigation.LOCATION);
                }
            }

            animate(mMainLayout, width, height, 0, 0, 200, 1);
            animate(mInterestLayout, width, height, 0, 0, 200, 0);
            animate(mPictureLayout, width, height, 0, 0, 200, 0);
            animate(mLocationLayout, width, height, 0, 0, 200, 0);

            mPictureView.animate().alpha(0).start();
            mInterestsView.animate().alpha(0).start();
            mLocationView.animate().alpha(0).start();

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float interestsDistance = getDistanceFromCenter(mInterestLayout, event.getX(), event.getY());
            if (interestsDistance < 2 * width && mMainLayout.getWidth() == extendedWidth && mMainLayout.getHeight() == extendedHeight) {
                if (mInterestsView.getAlpha() == 0 && interestsDistance < width) {
                    mInterestsView.setAlpha(1);
                    VibrationUtils.vibrate(getContext(), VibrationUtils.VIBRATION_TIME_SHORT);
                } else if (mInterestsView.getAlpha() == 1 && interestsDistance > width) {
                    mInterestsView.setAlpha(0);
                }
                Translation translation = getButtonTranslation(extendedWidth, extendedHeight, width, height, (2 * width - interestsDistance) / 7, (2 * width - interestsDistance) / 7, 1, 1);
                mInterestLayout.setTranslationX(translation.getX());
                mInterestLayout.setTranslationY(translation.getY());
            }

            float pictureDistance = getDistanceFromCenter(mPictureLayout, event.getX(), event.getY());
            if (pictureDistance < 2 * width && mMainLayout.getWidth() == extendedWidth && mMainLayout.getHeight() == extendedHeight) {
                if (mPictureView.getAlpha() == 0 && pictureDistance < width) {
                    mPictureView.setAlpha(1);
                    VibrationUtils.vibrate(getContext(), VibrationUtils.VIBRATION_TIME_SHORT);
                } else if (mPictureView.getAlpha() == 1 && pictureDistance > width) {
                    mPictureView.setAlpha(0);
                }
                Translation translation = getButtonTranslation(extendedWidth, extendedHeight, width, height, (2 * width - pictureDistance) / 5, 0, 1, -OFFSET);
                mPictureLayout.setTranslationX(translation.getX());
                mPictureLayout.setTranslationY(translation.getY());
            }

            float locationDistance = getDistanceFromCenter(mLocationLayout, event.getX(), event.getY());
            if (locationDistance < 2 * width && mMainLayout.getWidth() == extendedWidth && mMainLayout.getHeight() == extendedHeight) {
                if (mLocationView.getAlpha() == 0 && locationDistance < width) {
                    mLocationView.setAlpha(1);
                    VibrationUtils.vibrate(getContext(), VibrationUtils.VIBRATION_TIME_SHORT);
                } else if (mLocationView.getAlpha() == 1 && locationDistance > width) {
                    mLocationView.setAlpha(0);
                }
                Translation translation = getButtonTranslation(extendedWidth, extendedHeight, width, height, 0, (2 * width - locationDistance) / 5, -OFFSET, 1);
                mLocationLayout.setTranslationX(translation.getX());
                mLocationLayout.setTranslationY(translation.getY());
            }
        }
        return true;
    }

    private void animate(View view, int width, int height, float translationX, float translationY, int duration, float alpha) {
        ObjectAnimator buttonAnimator = ViewPropertyObjectAnimator.animate(view).withLayer().
                width(width).
                height(height).
                translationX(translationX).
                translationY(translationY).
                alpha(alpha).
                setDuration(duration).setInterpolator(new LinearOutSlowInInterpolator()).get();
        buttonAnimator.start();
    }

    private Translation getButtonTranslation(float layoutWidth, float layoutHeight, float buttonWidth, float buttonHeight, float offsetX, float offsetY, float vectorX, float vectorY) {
        float translationX = vectorX * (-offsetX - (layoutWidth - (vectorX * vectorY + 2) * buttonWidth) / 2);
        float translationY = vectorY * (-offsetY - (layoutHeight - (vectorX * vectorY + 2) * buttonHeight) / 2);
        Translation translation = new Translation(translationX, translationY);
        return translation;
    }

    private float getDistanceFromCenter(View view, float x, float y) {
        float xPosition = view.getLeft() + view.getTranslationX() + view.getHeight() / 2;
        float yPosition = view.getTop() + view.getTranslationY() + view.getWidth() / 2;
        return (float) Math.sqrt(Math.pow(x - xPosition, 2) + Math.pow(y - yPosition, 2));
    }

    private class Translation {
        private float x, y;

        public Translation(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }
}
