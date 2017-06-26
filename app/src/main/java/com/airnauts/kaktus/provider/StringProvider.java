package com.airnauts.kaktus.provider;

import android.content.Context;

import com.airnauts.toolkit.provider.BaseDataProvider;

/**
 * Created by mradziko on 06.05.2017.
 */

public class StringProvider extends BaseDataProvider {

    private Context mContext;

    @Override
    public void initialize(Context context) {
        mContext = context;
    }

    public String getString(int id){
        return mContext.getString(id);
    }
}
