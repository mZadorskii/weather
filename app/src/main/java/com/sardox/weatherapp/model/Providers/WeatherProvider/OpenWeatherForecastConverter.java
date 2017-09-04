package com.sardox.weatherapp.model.Providers.WeatherProvider;

import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.Coord;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.Main;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.Weather;

import java.util.List;


public class OpenWeatherForecastConverter {

        public static WeatherForecast toMyWeatherForecast(OpenWeatherForecast openWeather) {
        WeatherForecast weatherForecast = new WeatherForecast();

        Main main = openWeather.getMain();
        if (main != null) {
            if (main.getTemp() != null) weatherForecast.setTemp(main.getTemp().floatValue());
            if (main.getHumidity() != null)
                weatherForecast.setHumidity(main.getHumidity().floatValue());
        }

        if (openWeather.getName() != null) weatherForecast.setLocationName(openWeather.getName());
        Coord coord = openWeather.getCoord();
        if (coord != null) {
            if (coord.getLat() != null) weatherForecast.setLat(coord.getLat());
            if (coord.getLon() != null) weatherForecast.setLon(coord.getLon());
        }

        List<Weather> weatherList = openWeather.getWeather();
        if (!weatherList.isEmpty()) {
            Weather weather = weatherList.get(0); // TODO 0 index??
            if (weather.getDescription() != null) {
                weatherForecast.setWeather_description((weather.getDescription()));
            }
        }

        return weatherForecast;
    }
}
