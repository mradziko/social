package com.airnauts.kaktus.utils;

/**
 * Created by mradziko on 06.05.2017.
 */

public class SplashUtils {

    public interface SplashUtilsListener{
        public void onSplashDownload(String splash);
    }

    public void getSplash(final SplashUtilsListener listener){
        //TODO

        new Thread(new Runnable() {
            @Override
            public void run() {

                listener.onSplashDownload("SPLASH");
            }
        }).start();
    }

}
