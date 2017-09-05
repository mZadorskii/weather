package com.sardox.weatherapp.weather;

import android.util.Log;

import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.LocationKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.Utils;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationCallback;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import javax.inject.Inject;

/**
 * Created by sardox on 9/2/2017.
 */

public class WeatherPresenterImp implements WeatherPresenter, WeatherPresenterCallback {

    private static final String TAG = "WeatherPresenterImp";
    private WeatherView view;
    private MainModel mainModel;
    private final Utils.TEMPERATURE selectedTempUnit = Utils.TEMPERATURE.Fahrenheit;  //i can let user chose units later. for now - final


    @Inject
    public WeatherPresenterImp(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void onLocationReceived(MyLocation location) {
        view.showLoading();
        mainModel.getWeatherByKey(new LocationKey(location));
    }

    @Override
    public void onNetworkProviderDisabled() {
        view.onNetworkProviderDisabled();
    }

    @Override
    public void onViewPrepared() {
        mainModel.setupWeatherPresenterCallback(this);
        mainModel.getMostRecentItem(new Consumer<RecentItem>() {
            @Override
            public void onSuccess(RecentItem item) {
                if (item != null) mainModel.getWeatherByKey(item.getMyCacheKey());
            }

            @Override
            public void onFailed(String error) {
            }
        });
    }

    @Override
    public void onAttach(WeatherView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
         //mainModel.setupWeatherPresenterCallback(null);
    }

    @Override
    public void onLocationPermissionGranted() {
        view.showLoading();
        mainModel.getUserLocation(new LocationCallback() {
            @Override
            public void onLocationReceived(MyLocation location) {
                Log.v("weatherApp", TAG + " onLocationReceived");
                mainModel.getWeatherByKey(new LocationKey(location));
            }

            @Override
            public void onProviderDisabled() {
                Log.v("weatherApp", "onProviderDisabled123 NEVER CALLED ***************************************");
                view.hideLoading();
                view.onNetworkProviderDisabled();
            }
        });
    }

    @Override
    public void onLocationPermissionDenied() {
        view.askUserToEnablePermissions();
    }

    @Override
    public void getWeatherWithMyLocation() {
        view.checkPermissions();
    }

    @Override
    public void onNetworkError(String error) {
        view.showMessage("onNetworkError" + error);
    }

    @Override
    public void getWeatherByUserInput(final String user_input) {

        switch (Utils.validateInput(user_input)) {
            case CITY:
                view.showLoading();
                mainModel.getWeatherByKey(new CityKey(user_input));
                break;
            case ZIP:
                view.showLoading();
                mainModel.getWeatherByKey(new ZipKey(user_input));
                break;
            case UNKNOWN:
                view.showMessage("Please make sure you entered correct ZIP/City");
                break;
        }
    }

    @Override
    public void onWeatherReceiveError(String error) {
        view.hideLoading();
        view.showMessage(error);
    }

    @Override
    public void onWeatherReceived(WeatherForecast weatherForecast) {
        weatherForecast.setTemp(Utils.convertTemp(weatherForecast.getTemp(), selectedTempUnit));
        mainModel.addItemToRecent(weatherForecast);
        view.hideLoading();
        view.updateWeather(weatherForecast);
    }
}
