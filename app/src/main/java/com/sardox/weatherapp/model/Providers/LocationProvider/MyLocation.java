package com.sardox.weatherapp.model.Providers.LocationProvider;

/**
 * Created by sardox on 9/3/2017.
 */

public class MyLocation {
    private double lat;
    private double lon;

    public MyLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyLocation that = (MyLocation) o;

        if (Double.compare(that.lat, lat) != 0) return false;
        return Double.compare(that.lon, lon) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
