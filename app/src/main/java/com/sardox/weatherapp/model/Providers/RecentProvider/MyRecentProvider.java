package com.sardox.weatherapp.model.Providers.RecentProvider;


import android.util.Log;

import com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager.AppPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MyRecentProvider implements RecentProvider {
    private AppPref appPref;

    /**
     * proper key should be location{lat, lon} + name, not just name
     * for example, somewhere is pacific ocean api will return empty location
     */
    private HashMap<String, RecentItem> recentItemHashMap;

    @Inject
    public MyRecentProvider(HashMap<String, RecentItem> recentItemHashMap, AppPref appPref) {
        this.appPref = appPref;
        this.recentItemHashMap = recentItemHashMap;
        load();
    }

    @Override
    public void load() {
        recentItemHashMap.clear();
        List<RecentItem> allItems = new ArrayList<>();
        allItems = appPref.loadRecentItems(); //it may take a while. rework with callback
        if (allItems != null) {
            for (RecentItem item : allItems) {
                recentItemHashMap.put(item.getItemKey(), item);
            }
        }
    }

    @Override
    public void removeItem(RecentItem itemToRemove) {
        recentItemHashMap.remove(itemToRemove.getItemKey());
        appPref.saveItems(recentItemHashMap.values());
    }

    @Override
    public void addItem(RecentItem itemToAdd) {
        Log.v("weatherApp", "MyRecentProvider addItem with key: " + itemToAdd.getItemKey() + ", location: " + itemToAdd.getLocationName());
        recentItemHashMap.put(itemToAdd.getItemKey(), itemToAdd);
        appPref.saveItems(recentItemHashMap.values());
    }

    @Override
    public List<RecentItem> getRecentItems() {
        return new ArrayList<>(recentItemHashMap.values());
    }

    @Override
    public RecentItem getMostRecentItem() {
        if (recentItemHashMap.isEmpty()) return null; // // TODO: 9/2/2017   fix null
        RecentItem mostRecent = new RecentItem();
        for (RecentItem item : recentItemHashMap.values()) {
            if (mostRecent.getLastRequestedTimestamp() < item.getLastRequestedTimestamp()) {
                mostRecent = item;
            }
        }
        return mostRecent;
    }


}
