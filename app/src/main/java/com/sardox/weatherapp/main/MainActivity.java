package com.sardox.weatherapp.main;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.sardox.weatherapp.ApplicationClass;
import com.sardox.weatherapp.Dagger.ActivityComponent;
import com.sardox.weatherapp.Dagger.ActivityModule;
import com.sardox.weatherapp.Dagger.DaggerActivityComponent;
import com.sardox.weatherapp.R;
import com.sardox.weatherapp.weather.WeatherFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }

    private ActivityComponent mActivityComponent;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.fab)
    public void onClickFab() {

    }

    @Inject
    MainPresenter mainPresenter;


    @Inject
    SectionsPagerAdapter mSectionsPagerAdapter;

    //AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((ApplicationClass) getApplication()).getAppComponent())
                .build();
        mActivityComponent.inject(this);

        mainPresenter.attachView(this);

        //setting up
        mSectionsPagerAdapter.setCount(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2); // ??
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v("weatherApp", "MainActivity onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            FragmentManager fm = getSupportFragmentManager();
            for (Fragment fragment : fm.getFragments()) {
                if (fragment instanceof WeatherFragment)
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


    @Override
    public void showWeatherTab() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showRecentTab() {
        mViewPager.setCurrentItem(1);
    }
}
