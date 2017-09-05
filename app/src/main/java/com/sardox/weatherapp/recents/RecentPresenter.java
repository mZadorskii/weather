package com.sardox.weatherapp.recents;


import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

public interface RecentPresenter {

    void onViewPrepared();

    void onAttach(RecentView view);

    void onDetach();

    void onRecentClicked(RecentItem clickedItem);

}
