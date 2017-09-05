package com.sardox.weatherapp.model.Providers.RecentProvider;

import com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager.AppPref;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by sardox on 9/4/2017.
 */
public class MyRecentProviderTest {



    @Mock
    AppPref appPref;

    HashMap<MyCacheKey, RecentItem> recentItemHashMap;

    private MyRecentProvider myRecentProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        recentItemHashMap = new HashMap<>();
        myRecentProvider = new MyRecentProvider(recentItemHashMap,appPref);
    }

    @Test
    public void getMostRecentItem() throws Exception {
        RecentItem item1 = new RecentItem();
        item1.setMyCacheKey(new ZipKey("97202"));
        item1.setLastRequestedTimestamp(100);
        myRecentProvider.addItem(item1);

        RecentItem item2 = new RecentItem();
        item2.setMyCacheKey(new ZipKey("97203"));
        item2.setLastRequestedTimestamp(300);
        myRecentProvider.addItem(item2);

        RecentItem item3 = new RecentItem();
        item3.setMyCacheKey(new ZipKey("97204"));
        item3.setLastRequestedTimestamp(200);
        myRecentProvider.addItem(item3);

        assertEquals(3, myRecentProvider.getRecentItems().size());
        assertEquals(myRecentProvider.getMostRecentItem(), item2);
    }

}