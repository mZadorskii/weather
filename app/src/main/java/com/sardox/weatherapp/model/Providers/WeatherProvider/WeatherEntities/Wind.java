
package com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// i genereated it with http://www.jsonschema2pojo.org/
public class Wind {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

}
