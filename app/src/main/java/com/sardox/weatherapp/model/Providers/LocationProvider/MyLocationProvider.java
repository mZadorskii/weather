package com.sardox.weatherapp.model.Providers.LocationProvider;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.sardox.weatherapp.Dagger.ApplicationContext;

import javax.inject.Inject;

public class MyLocationProvider implements LocationProvider, LocationListener {

    private final Context context;
    private LocationManager mlocManager;
    private LocationCallback locationCallback;

    @Inject
    public MyLocationProvider(@ApplicationContext Context context) {
        this.context = context;
    }

    /*
    1. trying to get last know location based on network, if its there - calling  onLocationChanged
    2. if its not availiable, requesting NETWORK_PROVIDER SINGLE location. it can take a while...
    3. might not retrun location if user has no SIM and not connected to wifi. need to use GPS_provider as well
     */
    @Override
    public void getUserLocation(LocationCallback locationCallback) {
        this.locationCallback=locationCallback;
        if (mlocManager == null)
            mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Location location = getLastKnownLocation();


        if (location == null) {
            try {
                mlocManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            } catch (SecurityException e) {
                Log.e("test", "SecurityException should never happen. DEBUG IT ");
            }
        } else {
            onLocationChanged(location);
        }
    }


    private Location getLastKnownLocation() {
        try {
            return mlocManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException e) {
            Log.e("weatherApp", "SecurityException should never happen. DEBUG IT ");
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (locationCallback!=null) {
            locationCallback.onLocationReceived(new MyLocation(location.getLatitude(), location.getLongitude()));
            mlocManager.removeUpdates(this);
            locationCallback = null;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }
    private boolean isNetworkProviderEnabled() {
        return mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void onProviderDisabled(String s) {
        if (!isNetworkProviderEnabled()) {
            if (locationCallback!=null) locationCallback.onProviderDisabled();
        }
    }
}
