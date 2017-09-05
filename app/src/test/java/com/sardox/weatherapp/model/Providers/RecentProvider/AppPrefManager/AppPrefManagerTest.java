package com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager;

import android.content.Context;

import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;


/**
 * Created by sardox on 9/4/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppPrefManagerTest {
    private AppPrefManager appPrefManager;

    @Mock
    Context context;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        appPrefManager = new AppPrefManager(context);
    }

    @Test
    public void serializationTest() throws Exception {
        RecentItem item = new RecentItem();
        item.setMyCacheKey(new ZipKey("97209"));
        item.setFriendlyLocationName("Portland");

        Collection<RecentItem> items = new ArrayList<>();
        items.add(item);

        String json = appPrefManager.recentItemsToJson(items);
        assertEquals(appPrefManager.jsonToRecents(json).get(0), item);
    }
}