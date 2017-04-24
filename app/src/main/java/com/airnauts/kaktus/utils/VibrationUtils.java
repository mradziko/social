package com.airnauts.kaktus.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by mradziko on 30.11.2015.
 */
public class VibrationUtils {

    public static final long VIBRATION_TIME_SHORT = 10;

    public static void vibrate(Context context, long time) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(time);
    }
}
