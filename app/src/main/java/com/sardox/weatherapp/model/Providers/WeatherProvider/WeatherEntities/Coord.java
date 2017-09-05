package com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// i genereated it with http://www.jsonschema2pojo.org/
public class Coord {

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

}
