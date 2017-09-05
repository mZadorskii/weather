package com.sardox.weatherapp.weather;

import com.sardox.weatherapp.main.MainPresenterImpl;
import com.sardox.weatherapp.main.MainView;
import com.sardox.weatherapp.model.MainModel;
import com.sardox.weatherapp.model.Providers.LocationProvider.LocationCallback;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.WeatherForecast;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by sardox on 9/4/2017.
 */
public class WeatherPresenterImpTest {

    @Mock
    WeatherPresenterCallback weatherPresenterCallback;

    @Mock
    WeatherView view;

    @Mock
    MainModel mainModel;
    private  WeatherPresenterImp  weatherPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherPresenter = new WeatherPresenterImp(mainModel);
        mainModel.setupWeatherPresenterCallback(weatherPresenterCallback);
        weatherPresenter.onAttach(view);
    }

    @Test
    public void showLoadingOnLocationReceived() throws Exception {
        weatherPresenter.onLocationReceived(new MyLocation(10,10));
        verify(view, times(1)).showLoading();
    }

    @Test
    public void onNetworkProviderDisabled() throws Exception {
        weatherPresenter.onNetworkProviderDisabled();
        verify(view, times(1)).onNetworkProviderDisabled();
    }

    @Test
    public void onViewPreparedAndRecentItemsExistThanRequestWeather() throws Exception {
        final RecentItem o = mock(RecentItem.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<RecentItem>) invocation.getArguments()[0]).onSuccess(o);
                return null;
            }
        }).when(mainModel).getMostRecentItem(ArgumentMatchers.<Consumer>any());

        weatherPresenter.onViewPrepared();
        verify(mainModel, times(1)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void onViewPreparedAndRecentItemsNotExistThanDontRequestWeather() throws Exception {
        final RecentItem o = mock(RecentItem.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<RecentItem>) invocation.getArguments()[0]).onFailed("");
                return null;
            }
        }).when(mainModel).getMostRecentItem(ArgumentMatchers.<Consumer>any());

        weatherPresenter.onViewPrepared();
        verify(mainModel, times(0)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void onLocationPermissionGrantedAndonLocationReceived() throws Exception {
        final MyLocation o = mock(MyLocation.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((LocationCallback) invocation.getArguments()[0]).onLocationReceived(o);
                return null;
            }
        }).when(mainModel).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());

        weatherPresenter.onLocationPermissionGranted();
        verify(view, times(1)).showLoading();
        verify(mainModel, times(0)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void onLocationPermissionDenied() throws Exception {
        weatherPresenter.onLocationPermissionDenied();
        verify(view, times(1)).askUserToEnablePermissions();
    }

    @Test
    public void getWeatherByUserInputZipWeatherRequested() throws Exception {
        String input = "97209";
        weatherPresenter.getWeatherByUserInput(input);
        verify(view, times(1)).showLoading();
        verify(mainModel, times(1)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void getWeatherByUserInputCityWeatherRequested() throws Exception {
        String input = "Porltnad";
        weatherPresenter.getWeatherByUserInput(input);
        verify(view, times(1)).showLoading();
        verify(mainModel, times(1)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void getWeatherByUserInputEMPTYWeatherRequested() throws Exception {
        String input = "";
        weatherPresenter.getWeatherByUserInput(input);
        verify(view, times(0)).showLoading();
        verify(view, times(1)).showMessage(anyString());
        verify(mainModel, times(0)).getWeatherByKey(ArgumentMatchers.<MyCacheKey>any());
    }

    @Test
    public void onWeatherReceiveError() throws Exception {
        String error = "Porltnad";
        weatherPresenter.onWeatherReceiveError(error);
        verify(view, times(1)).hideLoading();
        verify(view, times(1)).showMessage(error);
    }

    @Test
    public void onWeatherReceived() throws Exception {
        WeatherForecast weatherForecast = mock(WeatherForecast.class);
        weatherPresenter.onWeatherReceived(weatherForecast);
        verify(mainModel, times(1)).addItemToRecent(weatherForecast);
        verify(view, times(1)).hideLoading();
        verify(view, times(1)).updateWeather(weatherForecast);
    }

}