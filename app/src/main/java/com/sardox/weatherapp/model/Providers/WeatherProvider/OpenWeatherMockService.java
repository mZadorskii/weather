package com.sardox.weatherapp.model.Providers.WeatherProvider;

import android.support.design.widget.AppBarLayout;

import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;

import retrofit2.Call;
import retrofit2.http.Query;


/**
 * Created by sardox on 9/3/2017.
 */

public class OpenWeatherMockService {
    static final class MockOpenWeatherServie implements OpenWeatherService{
        private final OpenWeatherForecast zip = new OpenWeatherForecast();

        MockOpenWeatherServie(){
            zip.setName("zip");
        }
        @Override

        public Call<OpenWeatherForecast> getWeatherByZIP(@Query("zip") String zip_code_and_county_code, @Query("APPID") String APPID) {
            return null;
        }

        @Override
        public Call<OpenWeatherForecast> getWeatherByCity(@Query("q") String city_name, @Query("APPID") String APPID) {
            return null;
        }

        @Override
        public Call<OpenWeatherForecast> getWeatherLatLon(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String APPID) {
            return null;
        }
    }
}
