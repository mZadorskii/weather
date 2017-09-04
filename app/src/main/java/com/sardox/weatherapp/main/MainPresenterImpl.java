package com.sardox.weatherapp.main;


import com.sardox.weatherapp.model.MainModel;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter, MainPresenterCallback{
    private static final String TAG = "MainPresenterImpl";
    private MainView view;
    private MainModel mainModel;

    @Inject
    public MainPresenterImpl(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public MainModel getDataManager() {
        return mainModel;
    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
        this.mainModel.setupMainPresenterCallback(this);
    }


    @Override
    public void detachView() {
        this.view = null;
        this.mainModel.setupMainPresenterCallback(null);
    }

    @Override
    public void onWeatherRequestedFromRecent() {
        this.view.showWeatherTab();
    }


}
