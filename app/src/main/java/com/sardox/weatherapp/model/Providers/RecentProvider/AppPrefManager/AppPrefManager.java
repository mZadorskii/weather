package com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.sardox.weatherapp.Dagger.ApplicationContext;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;

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
    private Gson gson;

    @Inject
    public AppPrefManager(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        gson = new GsonBuilder().registerTypeAdapter(MyCacheKey.class, new InterfaceAdapter<MyCacheKey>())
                .create();
    }

    public List<RecentItem> jsonToRecents(String itemsInJson){
        List<RecentItem> recentItems = new ArrayList<>();
        Type type = new TypeToken<List<RecentItem>>() {}.getType();
        recentItems = gson.fromJson(itemsInJson, type);
        if (recentItems != null) {
           // Log.v("weatherApp", "AppPrefManager loaded: " + recentItems.size()+ " items");
        }
        return  recentItems;
    }

    public String recentItemsToJson(Collection<RecentItem> items){
        return  gson.toJson(items);
    }

    @Override
    public List<RecentItem> loadRecentItems() {
        String itemsInJson = mPrefs.getString(PREF_KEY_USER_RECENT_ITEMS , null);
        return jsonToRecents(itemsInJson);
    }

    @Override
    public void saveItems(Collection<RecentItem> items) {
        mPrefs.edit().putString(PREF_KEY_USER_RECENT_ITEMS ,recentItemsToJson(items)).apply();
        //Log.v("weatherApp", "AppPrefManager saved: " + items.size() + " items");
    }



}
