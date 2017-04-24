package com.airnauts.kaktus.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mradziko on 8.12.2015.
 */

public class SquareSurfaceView extends GLSurfaceView {

    public SquareSurfaceView(Context context) {
        this(context, null);
    }

    public SquareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }
}