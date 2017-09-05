package com.sardox.weatherapp.main;

/*
this callback is used to switch from Recent to Weather tab when weather requested
 */
public interface MainPresenterCallback {
    void onWeatherRequestedFromRecent();
}
