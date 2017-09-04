package com.sardox.weatherapp.model.Providers.WeatherProvider.cache;

import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;

/**
 * Created by sardox on 9/3/2017.
 */

public class LocationKey implements MyCacheKey {
    private MyLocation location;

    public LocationKey(MyLocation location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationKey that = (LocationKey) o;

        return location != null ? location.equals(that.location) : that.location == null;

    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
