package com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager;

import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.util.Collection;
import java.util.List;

/**
 * Created by sardox on 9/2/2017.
 */

public interface AppPref {
    List<RecentItem> loadRecentItems();

    void saveItems(Collection<RecentItem> items);

}
