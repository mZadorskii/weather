package com.sardox.weatherapp.recents;


import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.util.List;

interface RecentView {
    void showLoading();
    void hideLoading();
    void updateList(List<RecentItem> newItems);

    void onError(String message);
    void showMessage(String message);
}
