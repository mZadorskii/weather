package com.sardox.weatherapp.model;


import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationCallback;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationProvider;

import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.OpenWeatherForecastConverter;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherProvider;
import com.sardox.weatherapp.main.MainPresenterCallback;
import com.sardox.weatherapp.recents.RecentPresenterCallback;
import com.sardox.weatherapp.weather.WeatherPresenterCallback;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton


public class MainModelImpl implements MainModel {
    private MainPresenterCallback mainPresenterCallback;
    private RecentPresenterCallback recentPresenterCallback;
    private WeatherPresenterCallback weatherPresenterCallback;
    private final WeatherProvider weatherProvider;
    private final LocationProvider locationProvider;
    private final RecentProvider recentProvider;

    @Inject
    public MainModelImpl(WeatherProvider weatherProvider, RecentProvider recentProvider, LocationProvider locationProvider) {
        this.weatherProvider = weatherProvider;
        this.locationProvider = locationProvider;
        this.recentProvider = recentProvider;
    }


    @Override
    public void setupWeatherPresenterCallback(WeatherPresenterCallback weatherPresenterCallback) {
        this.weatherPresenterCallback = weatherPresenterCallback;
    }



    @Override
    public void getWeatherByCity(final String city) {
        weatherProvider.getWeatherByCity(city, new Consumer<OpenWeatherForecast>() {
            @Override
            public void onSuccess(OpenWeatherForecast openWeatherForecast) {
                WeatherForecast weatherForecast = OpenWeatherForecastConverter.toMyWeatherForecast(openWeatherForecast);
                weatherForecast.setSearch_key(city);
                weatherPresenterCallback.onWeatherReceived(weatherForecast);
            }

            @Override
            public void onFailed(String error) {
                weatherPresenterCallback.onWeatherReceiveError(error);
            }
        });
    }

    @Override
    public void getWeatherByZip(final String zip) {
        weatherProvider.getWeatherByZip(zip, new Consumer<OpenWeatherForecast>() {
            @Override
            public void onSuccess(OpenWeatherForecast openWeatherForecast) {
                WeatherForecast weatherForecast = OpenWeatherForecastConverter.toMyWeatherForecast(openWeatherForecast);
                weatherForecast.setSearch_key(zip);
                weatherPresenterCallback.onWeatherReceived(weatherForecast);

            }

            @Override
            public void onFailed(String error) {
                weatherPresenterCallback.onWeatherReceiveError(error);
            }
        });
    }

    @Override
    public void getWeatherByLatLon(MyLocation location) {
        mainPresenterCallback.onWeatherRequestedFromRecent();// we need to notify  MainPresenter about new weather was fethced and switch tab to WeatherFragment

        weatherProvider.getWeatherByLatLon(location, new Consumer<OpenWeatherForecast>() {
            @Override
            public void onSuccess(OpenWeatherForecast openWeatherForecast) {
                WeatherForecast weatherForecast = OpenWeatherForecastConverter.toMyWeatherForecast(openWeatherForecast);
                weatherForecast.setSearch_key(weatherForecast.getLocationName());
                weatherPresenterCallback.onWeatherReceived(weatherForecast);

            }

            @Override
            public void onFailed(String error) {
                weatherPresenterCallback.onWeatherReceiveError(error);
            }
        });
    }

    @Override
    public void getRecentItems(Consumer<List<RecentItem>> callback) {
        callback.onSuccess(recentProvider.getRecentItems());
    }

    @Override
    public void getMostRecentItem(Consumer<RecentItem> callback) {
        callback.onSuccess(recentProvider.getMostRecentItem());
    }

    @Override
    public void addItemToRecent(WeatherForecast weatherForecast) {
        RecentItem recentItem = new RecentItem();
        recentItem.setLat(weatherForecast.getLat());
        recentItem.setLon(weatherForecast.getLon());
        recentItem.setItemKey(weatherForecast.getSearch_key());
        recentItem.setLastRequestedTimestamp(DateTime.now().getMillis());
        recentItem.setLocationName(weatherForecast.getLocationName());
        recentProvider.addItem(recentItem);
        recentPresenterCallback.onNewRecentAdded(); // we need to notify RecentPresenter about new item to update its recycler view
    }

    @Override
    public void setupMainPresenterCallback(MainPresenterCallback callback) {
        mainPresenterCallback = callback;
    }

    @Override
    public void setupRecentCallbacks(RecentPresenterCallback recentPresenterCallback) {
        this.recentPresenterCallback = recentPresenterCallback;
    }


    @Override
    public void getUserLocation(LocationCallback locationCallback) {
        locationProvider.getUserLocation(new LocationCallback() {
            @Override
            public void onLocationReceived(MyLocation location) {
                weatherPresenterCallback.onLocationReceived(location);
            }

            @Override
            public void onProviderDisabled() {
                weatherPresenterCallback.onNetworkProviderDisabled();
            }
        });
    }

}
