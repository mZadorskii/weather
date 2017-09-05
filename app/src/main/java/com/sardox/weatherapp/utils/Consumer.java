package com.sardox.weatherapp.utils;


public interface Consumer<T> {
    void onSuccess(T var1);

    void onFailed(String error);
}