package com.airnauts.kaktus.parse.manager;

public class AccountManager {

    private static final long LOCATION_TIMEOUT = 10000;

    private String mCurrentAddress;
    private boolean mHasLocation = false;

    private static AccountManager mInstance;

    public static void initialize() {
        mInstance = new AccountManager();
    }


    public static AccountManager getInstance() {
        return mInstance;
    }


}