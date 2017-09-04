package com.sardox.weatherapp.model;

import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherProvider;
import com.sardox.weatherapp.main.MainPresenterCallback;
import com.sardox.weatherapp.weather.WeatherPresenterCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by sardox on 9/2/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainModelImplTest {

    @Mock
    WeatherProvider weatherProvider;

    @Mock
    RecentProvider recentProvider;

    @Mock
    com.sardox.weatherapp.model.Providers.LocationProvider.LocationProvider locationProvider;

    @Mock
    MainModelImpl mainModel;

    @Mock
    WeatherPresenterCallback weatherPresenterCallback;

    @Mock
    MainPresenterCallback mainPresenterCallback;

    @Captor
    private ArgumentCaptor<String> captor;

    @Captor
    private ArgumentCaptor<WeatherForecast> captor2;

    @Test
    public void getWeatherByCityOld() throws Exception {
//        String city = "Portland";
//        mainModel.setupWeatherPresenterCallback(weatherPresenterCallback);
//        mainModel.getWeatherByCity(city);
//        verify(mainModel, times(1)).getWeatherByCity(captor.capture());
//        assertEquals(city, captor.getValue());
    }

    @Mock
    OpenWeatherForecast weather;

    @Test
    public void getWeatherByCity() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onSuccess(weather);
                return null;
            }
        }).when(weatherProvider).getWeatherByCity(ArgumentMatchers.<String>any(), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());

        MainModelImpl mainModel = new MainModelImpl(weatherProvider, recentProvider, locationProvider);
        mainModel.setupWeatherPresenterCallback(new WeatherPresenterCallback() {
            @Override
            public void onWeatherReceived(WeatherForecast weatherForecast) {
                assertEquals(weatherForecast.getSearch_key(), "Portland");
            }
            @Override
            public void onWeatherReceiveError(String error) {
            }
            @Override
            public void onLocationReceived(MyLocation location) {
            }
            @Override
            public void onNetworkProviderDisabled() {
            }
        });

        mainModel.getWeatherByCity("Portland");
        verify(weatherProvider, times(1)).getWeatherByCity(eq("Portland"), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getWeatherByZip() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onSuccess(weather);
                return null;
            }
        }).when(weatherProvider).getWeatherByZip(ArgumentMatchers.<String>any(), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());

        MainModelImpl mainModel = new MainModelImpl(weatherProvider, recentProvider, locationProvider);
        mainModel.setupWeatherPresenterCallback(new WeatherPresenterCallback() {
            @Override
            public void onWeatherReceived(WeatherForecast weatherForecast) {
                assertEquals(weatherForecast.getSearch_key(), "33181");
            }

            @Override
            public void onWeatherReceiveError(String error) {

            }

            @Override
            public void onLocationReceived(MyLocation location) {

            }

            @Override
            public void onNetworkProviderDisabled() {

            }
        });

        mainModel.getWeatherByZip("33181");
        verify(weatherProvider, times(1)).getWeatherByZip(eq("33181"), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getWeatherByLat() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onSuccess(weather);
                return null;
            }
        }).when(weatherProvider).getWeatherByLatLon(ArgumentMatchers.<MyLocation>any(), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());

        MainModelImpl mainModel = new MainModelImpl(weatherProvider, recentProvider, locationProvider);
        mainModel.setupMainPresenterCallback(mainPresenterCallback);
        mainModel.setupWeatherPresenterCallback(new WeatherPresenterCallback() {
            @Override
            public void onWeatherReceived(WeatherForecast weatherForecast) {
                assertEquals(weatherForecast.getSearch_key(), "");
            }

            @Override
            public void onWeatherReceiveError(String error) {

            }

            @Override
            public void onLocationReceived(MyLocation location) {

            }

            @Override
            public void onNetworkProviderDisabled() {

            }
        });

        mainModel.getWeatherByLatLon(new MyLocation(10,20));
        verify(weatherProvider, times(1)).getWeatherByLatLon(eq(new MyLocation(10,20)), ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getRecentItems() throws Exception {
        MainModelImpl mainModel = new MainModelImpl(weatherProvider, recentProvider, locationProvider);
        mainModel.getRecentItems(new Consumer<List<RecentItem>>() {
            @Override
            public void onSuccess(List<RecentItem> var1) {

            }

            @Override
            public void onFailed(String error) {

            }
        });


        verify(recentProvider, times(1)).getRecentItems();

    }
}