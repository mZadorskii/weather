package com.sardox.weatherapp.Dagger;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.sardox.weatherapp.main.SectionsPagerAdapter;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.main.MainPresenter;
import com.sardox.weatherapp.main.MainPresenterImpl;
import com.sardox.weatherapp.recents.RecentAdapter;
import com.sardox.weatherapp.recents.RecentPresenter;
import com.sardox.weatherapp.recents.RecentPresenterImpl;
import com.sardox.weatherapp.weather.WeatherPresenter;
import com.sardox.weatherapp.weather.WeatherPresenterImp;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    MainPresenter provideMainPresneterImpl(MainPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    WeatherPresenter provideWeatherPresenter(WeatherPresenterImp weatherPresenterImp) {
        return weatherPresenterImp;
    }

    @Provides
    RecentPresenter provideRecentPresenter(RecentPresenterImpl weatherPresenterImp) {
        return weatherPresenterImp;
    }

    @Provides
    RecentAdapter provideRecentAdapter() {
        return new RecentAdapter(new ArrayList<RecentItem>());
    }

    @Provides
    SectionsPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new SectionsPagerAdapter(activity.getSupportFragmentManager());
    }
}