package com.airnauts.kaktus.provider.listener;

import com.airnauts.toolkit.provider.BaseDataProvider;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mradziko on 10.11.2016.
 */

public interface AuthProviderListener extends BaseDataProvider.BaseDataProviderListener {

    public void authenticationStateChanged(FirebaseUser user);

}
