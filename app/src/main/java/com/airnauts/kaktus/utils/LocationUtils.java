package com.airnauts.kaktus.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;

/**
 * Created by mradziko on 21.12.2015.
 */
public class LocationUtils implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static LocationSource.OnLocationChangedListener mListener;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    private static LocationUtils mInstance;

    public static LocationUtils getInstance(Context context) {
        synchronized (LocationUtils.class) {
            if (mInstance == null) {
                mInstance = new LocationUtils(context);
            }
        }
        return mInstance;
    }

    protected LocationUtils(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public Location getLocation() {
        return mLocation;
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            if (mListener != null) {
                mListener.onLocationChanged(mLocation);
            }
            Log.i(LocationUtils.class.getSimpleName(), "Latitude = " + mLocation.getLatitude());
        } else {
            Log.i(LocationUtils.class.getSimpleName(), "Connection failed: ConnectionResult.getErrorCode()");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(LocationUtils.class.getSimpleName(), "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(LocationUtils.class.getSimpleName(), "Connection suspended");
        mGoogleApiClient.connect();
    }

}
