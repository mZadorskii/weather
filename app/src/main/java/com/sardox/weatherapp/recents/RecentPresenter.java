package com.sardox.weatherapp.recents;


import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

import java.util.List;

public interface RecentPresenter {

    void onViewPrepared();

    void onAttach(RecentView view);

    void onDetach();

    void onRecentClicked(RecentItem clickedItem);

    void removeItems(List<MyCacheKey> listOfSelectedItems);
}
