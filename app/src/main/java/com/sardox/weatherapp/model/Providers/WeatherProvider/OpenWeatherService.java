package com.sardox.weatherapp.model.Providers.WeatherProvider;

import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//api.openweathermap.org/data/2.5/weather?q={city name}
//api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
//api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}

public interface OpenWeatherService {

    @GET("weather")
    Call<OpenWeatherForecast> getWeatherByZIP(@Query("zip") String zip_code_and_county_code,
                                              @Query("APPID") String APPID);

    @GET("weather")
    Call<OpenWeatherForecast> getWeatherByCity(@Query("q") String city_name,
                                               @Query("APPID") String APPID);

    @GET("weather")
    Call<OpenWeatherForecast> getWeatherLatLon(@Query("lat") double lat,
                                               @Query("lon") double lon,
                                               @Query("APPID") String APPID);
}
