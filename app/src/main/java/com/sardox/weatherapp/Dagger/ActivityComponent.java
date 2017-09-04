package com.sardox.weatherapp.Dagger;


import com.sardox.weatherapp.main.MainActivity;
import com.sardox.weatherapp.recents.RecentFragment;
import com.sardox.weatherapp.weather.WeatherFragment;

import dagger.Component;


@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(WeatherFragment fragment);
    void inject(RecentFragment fragment);
}