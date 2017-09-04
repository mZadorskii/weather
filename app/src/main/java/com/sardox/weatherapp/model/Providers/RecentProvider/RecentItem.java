package com.sardox.weatherapp.model.Providers.RecentProvider;


public class RecentItem {
    private String itemKey; // ==userInput
    private String locationName; // ==user friendly locationName
    private double lat = 0;
    private double lon =0;
    private long lastRequestedTimestamp=0;

    public RecentItem() {
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public long getLastRequestedTimestamp() {
        return lastRequestedTimestamp;
    }

    public void setLastRequestedTimestamp(long lastRequestedTimestamp) {
        this.lastRequestedTimestamp = lastRequestedTimestamp;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
