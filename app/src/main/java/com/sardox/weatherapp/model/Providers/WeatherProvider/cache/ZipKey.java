package com.sardox.weatherapp.model.Providers.WeatherProvider.cache;

/**
 * Created by sardox on 9/3/2017.
 */

public class ZipKey implements MyCacheKey {
    private String zip;

    public String getZip() {
        return zip;
    }

    public ZipKey(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZipKey zipKey = (ZipKey) o;

        return zip != null ? zip.equals(zipKey.zip) : zipKey.zip == null;

    }

    @Override
    public int hashCode() {
        return zip != null ? zip.hashCode() : 0;
    }
}
