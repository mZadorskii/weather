package com.sardox.weatherapp.utils;



public class WeatherForecast {
    private String search_key = "";
    private String locationName = "";
    private double lat = 0;
    private double lon =0;
    private String weather_description = "";
    private float temp = 0;
    private float humidity = 0;

    public WeatherForecast() {
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }


    public String getSearch_key() {
        return search_key;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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
