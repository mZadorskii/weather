package com.sardox.weatherapp.model.Providers.WeatherProvider.cache;

/**
 * Created by sardox on 9/3/2017.
 */

public class CityKey implements MyCacheKey {
    public String getCity() {
        return city;
    }

    private String city;

    public CityKey(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityKey zipKey = (CityKey) o;

        return city != null ? city.equals(zipKey.city) : zipKey.city == null;

    }

    @Override
    public int hashCode() {
        return city != null ? city.hashCode() : 0;
    }
}
