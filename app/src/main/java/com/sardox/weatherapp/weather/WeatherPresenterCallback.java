package com.sardox.weatherapp.weather;

import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.utils.WeatherForecast;

/**
 * Created by sardox on 9/2/2017.
 */

public interface WeatherPresenterCallback {
    void onWeatherReceived(WeatherForecast weatherForecast);

    void onWeatherReceiveError(String error);

    void onLocationReceived(MyLocation location);

    void onNetworkProviderDisabled();
}
