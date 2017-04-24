package com.airnauts.kaktus.provider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.airnauts.kaktus.R;
import com.airnauts.kaktus.provider.listener.AuthProviderListener;
import com.airnauts.toolkit.provider.BaseDataProvider;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mradziko on 10.11.2016.
 */

public class AuthProvider extends BaseDataProvider<AuthProviderListener> {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN = 9001;

    @Override
    public void initialize(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                notifyListeners(new Notifier() {
                    @Override
                    public void notify(AuthProviderListener authProviderListener) {
                        authProviderListener.authenticationStateChanged(user);
                    }
                });
            }
        };
    }

    @Override
    public void postAttach(AuthProviderListener listener) {
        super.postAttach(listener);
        final FirebaseUser user = mAuth.getCurrentUser();
        notifyListeners(new Notifier() {
            @Override
            public void notify(AuthProviderListener authProviderListener) {
                authProviderListener.authenticationStateChanged(user);
            }
        });
    }

    public void startAuth(Activity activity) {
        activity.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.drawable.logo_googleg_color_18dp)
                        .setProviders(getSelectedProviders())
                        .setTosUrl("www.wp.pl")
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    public void logOut(Activity activity) {
        AuthUI.getInstance().signOut(activity);
    }


    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();

        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER)
                .setPermissions(new ArrayList<String>() {{
                    add("user_friends");
                    add("user_photos");
                }})
                .build());

        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER)
                .setPermissions(new ArrayList<String>() {{
                    add(Scopes.GAMES);
                    add(Scopes.DRIVE_FILE);
                }})
                .build());

        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build());
        return selectedProviders;
    }
}
