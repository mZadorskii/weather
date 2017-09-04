package com.sardox.weatherapp.model.Providers.WeatherProvider;


import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;

public interface WeatherProvider {
    void getWeatherByCity(String city, Consumer<OpenWeatherForecast>  consumer);
    void getWeatherByZip(String zip, Consumer<OpenWeatherForecast>  consumer);
    void getWeatherByLatLon(MyLocation location, Consumer<OpenWeatherForecast>  consumer);
}
