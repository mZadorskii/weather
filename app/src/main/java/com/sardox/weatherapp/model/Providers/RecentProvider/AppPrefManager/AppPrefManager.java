package com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sardox.weatherapp.Dagger.ApplicationContext;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sardox on 9/2/2017.
 */

public class AppPrefManager implements AppPref {

    private static final String PREF_KEY_USER_RECENT_ITEMS = "PREF_KEY_USER_RECENT_ITEMS";
    private final SharedPreferences mPrefs;
    private final String prefFile = "weatherAppPrefs";

    @Inject
    public AppPrefManager(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
    }


    @Override
    public List<RecentItem> loadRecentItems() {
        String itemsInJson = mPrefs.getString(PREF_KEY_USER_RECENT_ITEMS , null);
        Gson gson = new Gson();

        List<RecentItem> recentItems = new ArrayList<>();
        Type type = new TypeToken<List<RecentItem>>() {}.getType();
        recentItems = gson.fromJson(itemsInJson, type);
        if (recentItems != null) {
            Log.v("weatherApp", "AppPrefManager loaded: " + recentItems.size()+ " items");
        }
        return recentItems;
    }

    @Override
    public void saveItems(Collection<RecentItem> items) {
        Gson gson = new Gson();
        String itemsInJson = gson.toJson(items);
        mPrefs.edit().putString(PREF_KEY_USER_RECENT_ITEMS ,itemsInJson).apply();
        Log.v("weatherApp", "AppPrefManager saved: " + items.size() + " items");
    }


}
