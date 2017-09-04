package com.sardox.weatherapp.Dagger;

import android.app.Application;
import android.content.Context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sardox.weatherapp.ApplicationClass;
import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.MainModelImpl;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationProvider;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocationProvider;
import com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager.AppPref;
import com.sardox.weatherapp.model.Providers.RecentProvider.AppPrefManager.AppPrefManager;
import com.sardox.weatherapp.model.Providers.RecentProvider.MyRecentProvider;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.MyCachedWeatherProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.OpenWeatherService;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    private final ApplicationClass mApplication;

    public AppModule(ApplicationClass application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Application providesApplicationClass() {
        return mApplication;
    }

    @Provides
    @Singleton
    MainModel providesMainModel(MainModelImpl mainModel) {
        return mainModel;
    }

    //---------------------------------------------------------------------------------------------------

    @Provides
    @Singleton
    RecentProvider provideMyRecentProvider(MyRecentProvider myRecentProvider) {
        return myRecentProvider;
    }

    @Provides
    @Singleton
    LocationProvider provideMyLocationProvider(MyLocationProvider myLocationProvider) {
        return myLocationProvider;
    }

    @Provides
    @Singleton
    WeatherProvider provideWeatherProvider(MyCachedWeatherProvider myCachedWeatherProvider) {
        return myCachedWeatherProvider;
    }

    @Singleton
    @Provides
    HashMap<MyCacheKey, RecentItem> recentItemHashMap() {
        return new HashMap<>();
    }

    @Singleton
    @Provides
    Cache<MyCacheKey, OpenWeatherForecast> providesWeatherCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(100)                        // maximum number of entries
                .expireAfterWrite(10, TimeUnit.MINUTES)  //// 10 min TTL  items will stay in cache for 10 min
                .build();
    }

    @Provides
    @Singleton
    AppPref provideAppPref(AppPrefManager appPrefManager) {
        return appPrefManager;
    }

    @Provides
    @Singleton
    OpenWeatherService provideOpenWeatherService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}