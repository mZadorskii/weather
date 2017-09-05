package com.sardox.weatherapp.model.Providers.RecentProvider;


import java.util.List;

public interface RecentProvider {

    void load();

    void removeItem(RecentItem itemToRemove);

    void addItem(RecentItem itemToAdd);

    List<RecentItem> getRecentItems();

    RecentItem getMostRecentItem();
}
