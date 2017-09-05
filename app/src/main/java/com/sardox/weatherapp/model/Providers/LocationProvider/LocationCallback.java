package com.sardox.weatherapp.model.Providers.LocationProvider;

/**
 * Created by sardox on 9/3/2017.
 */

public interface LocationCallback {

    void onLocationReceived(MyLocation location);

    void onProviderDisabled();

}
