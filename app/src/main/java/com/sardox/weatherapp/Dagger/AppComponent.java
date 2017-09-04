package com.sardox.weatherapp.Dagger;


import android.content.Context;

import com.sardox.weatherapp.ApplicationClass;
import com.sardox.weatherapp.model.MainModel;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ApplicationClass applicationClass);

    @ApplicationContext
    Context context();

    MainModel getMainModel();
}