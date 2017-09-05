package com.sardox.weatherapp.model;


import com.sardox.weatherapp.main.MainPresenterCallback;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationCallback;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.recents.RecentPresenterCallback;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.weather.WeatherPresenterCallback;

import java.util.List;

public interface MainModel {


    void getRecentItems(Consumer<List<RecentItem>> callback);

    void getMostRecentItem(Consumer<RecentItem> callback);

    void addItemToRecent(WeatherForecast weatherForecast);

    void getWeatherByKey(MyCacheKey key);

    void setupMainPresenterCallback(MainPresenterCallback callback);

    void setupRecentCallbacks(RecentPresenterCallback recentPresenterCallback);

    void setupWeatherPresenterCallback(WeatherPresenterCallback weatherPresenterCallback);

    void getUserLocation(LocationCallback locationCallback);
}
