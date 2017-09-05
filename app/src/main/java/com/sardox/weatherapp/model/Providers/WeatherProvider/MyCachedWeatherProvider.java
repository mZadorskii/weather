package com.sardox.weatherapp.model.Providers.WeatherProvider;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.cache.Cache;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.LocationKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCachedWeatherProvider implements WeatherProvider {
    private OpenWeatherService openWeatherService;
    private Cache<MyCacheKey, OpenWeatherForecast> weatherCache; // todo rework with LoadingCache if possible
    private final String apiKey = "95d190a434083879a6398aafd54d9e73";

    @Inject
    public MyCachedWeatherProvider(final OpenWeatherService openWeatherService, Cache<MyCacheKey, OpenWeatherForecast> weatherCache) {
        this.openWeatherService = openWeatherService;
        this.weatherCache = weatherCache;

    }

    @Override
    public void getWeatherByCity(final String city, final Consumer<OpenWeatherForecast> consumer) {
        //Log.v("weatherApp", "getWeatherByCity for: " + city);
        OpenWeatherForecast cachedOpenWeatherForecast = weatherCache.getIfPresent(new CityKey(city));
        if (cachedOpenWeatherForecast != null) {
            //Log.v("weatherApp", "getWeatherByCity is retrieved from cache");
            consumer.onSuccess(cachedOpenWeatherForecast);
            return;
        }
        openWeatherService.getWeatherByCity(city, apiKey).enqueue(new Callback<OpenWeatherForecast>() {
            @Override
            public void onResponse
                    (@NonNull Call<OpenWeatherForecast> call, @NonNull Response<OpenWeatherForecast> response) {

                //Log.v("weatherApp", "getWeatherByCity onResponse");
                if (response.isSuccessful()) {
                    OpenWeatherForecast receivedForecast = response.body();
                    if (receivedForecast != null) {
                        weatherCache.put(new CityKey(city), receivedForecast);
                        consumer.onSuccess(response.body());
                        return;
                    }
                }
                //Log.v("weatherApp", "getWeatherByCity onResponse is not Successful");
                consumer.onFailed("Unable to get weather.."); //TODO REWORK
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherForecast> call, @NonNull Throwable t) {
                //Log.v("weatherApp", "getWeatherByCity onFailure");
                consumer.onFailed("Unable to connect to weather service.."); //TODO REWORK
            }
        });
    }

    @Override
    public void getWeatherByZip(final String zip, final Consumer<OpenWeatherForecast> consumer) {
//        Log.v("weatherApp", "getWeatherByZip for: " + zip);
        OpenWeatherForecast cachedOpenWeatherForecast = weatherCache.getIfPresent(new ZipKey(zip));

        if (cachedOpenWeatherForecast != null) {
      //      Log.v("weatherApp", "getWeatherByZip is retrieved from cache");
            consumer.onSuccess(cachedOpenWeatherForecast);
            return;
        }
        openWeatherService.getWeatherByZIP(zip, apiKey).enqueue(new Callback<OpenWeatherForecast>() {
            @Override
            public void onResponse
                    (@NonNull Call<OpenWeatherForecast> call, @NonNull Response<OpenWeatherForecast> response) {
           //     Log.v("weatherApp", "getWeatherByZip onResponse");
                if (response.isSuccessful()) {
                    OpenWeatherForecast receivedForecast = response.body();
                    if (receivedForecast != null) {
                        weatherCache.put(new ZipKey(zip), receivedForecast);
                        consumer.onSuccess(response.body());
                        return;
                    }
                }

              //  Log.v("weatherApp", "getWeatherByZip onResponse is not Successful");
                consumer.onFailed("Unable to get weather.."); //TODO REWORK
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherForecast> call, @NonNull Throwable t) {
              //  Log.v("weatherApp", "getWeatherByZip onFailure");
                consumer.onFailed("Unable to connect to weather service.."); //TODO REWORK
            }
        });
    }

    @Override
    public void getWeatherByLatLon(final MyLocation location, final Consumer<OpenWeatherForecast> consumer) {
        //Log.v("weatherApp", "getWeatherByLatLon for: " + location.getLat() + ", " + location.getLon());
        OpenWeatherForecast cachedOpenWeatherForecast = weatherCache.getIfPresent(new LocationKey(location));
        if (cachedOpenWeatherForecast != null) {
            Log.v("weatherApp", "getWeatherByLatLon is retrieved from cache");
            consumer.onSuccess(cachedOpenWeatherForecast);
            return;
        }
        openWeatherService.getWeatherLatLon(location.getLat(), location.getLon(), apiKey).enqueue(new Callback<OpenWeatherForecast>() {
            @Override
            public void onResponse
                    (@NonNull Call<OpenWeatherForecast> call, @NonNull Response<OpenWeatherForecast> response) {
                //Log.v("weatherApp", "getWeatherByLatLon onResponse");
                if (response.isSuccessful()) {
                    OpenWeatherForecast receivedForecast = response.body();
                    if (receivedForecast != null) {
                        weatherCache.put(new LocationKey(location), receivedForecast);
                        consumer.onSuccess(response.body());
                        return;
                    }
                }

                //Log.v("weatherApp", "getWeatherByLatLon onResponse is not Successful");
                consumer.onFailed("Unable to get weather.."); //TODO REWORK
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherForecast> call, @NonNull Throwable t) {
                //Log.v("weatherApp", "getWeatherByZip onFailure");
                consumer.onFailed("Unable to connect to weather service.."); //TODO REWORK
            }
        });
    }

}
