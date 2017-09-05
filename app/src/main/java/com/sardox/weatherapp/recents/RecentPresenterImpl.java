package com.sardox.weatherapp.recents;

import android.util.Log;

import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by sardox on 9/2/2017.
 */

public class RecentPresenterImpl implements RecentPresenter, RecentPresenterCallback{
    private static final String TAG = "RecentPresenterImpl";
    private RecentView view;
    private MainModel mainModel;

    @Inject
    public RecentPresenterImpl(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void onViewPrepared() {
        view.showLoading();
        mainModel.setupRecentCallbacks(this);
        loadItems();
    }

    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void removeItems(List<MyCacheKey> listOfSelectedItems) {
        mainModel.removeItems(listOfSelectedItems);
        loadItems();
    }

    private void loadItems(){
        mainModel.getRecentItems(new Consumer<List<RecentItem>>() {
            @Override
            public void onSuccess(List<RecentItem> items) {
                if (!isViewAttached()) return;
                view.hideLoading();
                view.updateList(items);
            }

            @Override
            public void onFailed(String error) {
                if (!isViewAttached()) return;
                view.hideLoading();
                view.showMessage(error);
            }
        });
    }
    @Override
    public void onAttach(RecentView view) {
        this.view = view;
    }

    @Override
    public void onRecentClicked(RecentItem clickedItem) {
        mainModel.getWeatherByKey(clickedItem.getMyCacheKey());
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onNewRecentAdded() {
        if (view!=null) loadItems();
    }
}
