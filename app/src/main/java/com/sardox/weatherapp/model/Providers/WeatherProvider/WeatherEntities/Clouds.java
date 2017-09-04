
package com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// i genereated it with http://www.jsonschema2pojo.org/
public class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}
