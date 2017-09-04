package com.sardox.weatherapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.sardox.weatherapp.Dagger.AppComponent;
import com.sardox.weatherapp.Dagger.AppModule;
import com.sardox.weatherapp.Dagger.DaggerAppComponent;
import com.sardox.weatherapp.model.MainModel;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

public class ApplicationClass extends Application{
    private AppComponent component;

    @Inject
    MainModel model;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("dagger", "AppComponent created");
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }

    public static AppComponent getAppComponent(Context context) {
        return ((ApplicationClass) context.getApplicationContext()).component;
    }

    public AppComponent getAppComponent() {
        return component;
    }

}
