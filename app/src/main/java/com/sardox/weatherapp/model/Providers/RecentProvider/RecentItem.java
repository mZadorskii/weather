package com.sardox.weatherapp.model.Providers.RecentProvider;


import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

public class RecentItem {
    private MyCacheKey myCacheKey;
    private String friendlyLocationName; // ==user friendly friendlyLocationName
    private long lastRequestedTimestamp=0;

    public RecentItem() {
    }

    public String getFriendlyLocationName() {
        return friendlyLocationName;
    }

    public void setFriendlyLocationName(String friendlyLocationName) {
        this.friendlyLocationName = friendlyLocationName;
    }


    public long getLastRequestedTimestamp() {
        return lastRequestedTimestamp;
    }

    public void setLastRequestedTimestamp(long lastRequestedTimestamp) {
        this.lastRequestedTimestamp = lastRequestedTimestamp;
    }

    public MyCacheKey getMyCacheKey() {
        return myCacheKey;
    }

    public void setMyCacheKey(MyCacheKey myCacheKey) {
        this.myCacheKey = myCacheKey;
    }
}
