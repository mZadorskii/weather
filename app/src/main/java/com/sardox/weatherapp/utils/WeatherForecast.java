package com.sardox.weatherapp.utils;


import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

public class WeatherForecast {
    //private String search_key = "";
    private String locationName = "";
    private MyCacheKey key;

    public MyCacheKey getKey() {
        return key;
    }

    public void setKey(MyCacheKey key) {
        this.key = key;
    }

    private String weather_description = "";
    private float temp = 0;
    private float humidity = 0;

    public WeatherForecast() {
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "weather_description: " + weather_description + "\ntemp: " + temp + "\nhumidity: " + humidity;
    }

}
