package com.sardox.weatherapp.model.Providers.RecentProvider;



import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

import java.util.List;

public interface RecentProvider {

    void load();
    void removeItems(List<MyCacheKey> selectedItems);
    void addItem(RecentItem itemToAdd);
    List<RecentItem>  getRecentItems();
    RecentItem getMostRecentItem();

}
