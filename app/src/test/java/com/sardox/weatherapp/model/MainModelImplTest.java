package com.sardox.weatherapp.model;

import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.LocationKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.utils.WeatherForecast;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentItem;
import com.sardox.weatherapp.model.Providers.RecentProvider.RecentProvider;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherProvider;
import com.sardox.weatherapp.main.MainPresenterCallback;
import com.sardox.weatherapp.weather.WeatherPresenterCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import retrofit2.Callback;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    MainModelImpl mainModel;

    @Mock
    WeatherPresenterCallback weatherPresenterCallback;

    @Mock
    MainPresenterCallback mainPresenterCallback;

    @Captor
    private ArgumentCaptor<CityKey> captor;

    @Captor
    private ArgumentCaptor<WeatherForecast> captor2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainModel = new MainModelImpl(weatherProvider,recentProvider,locationProvider);
        mainModel.setupWeatherPresenterCallback(weatherPresenterCallback);
        mainModel.setupMainPresenterCallback(mainPresenterCallback);
    }

    @Test
    public void getWeatherByCityCalledWeatherProviderTest() throws Exception {
        String city = "Portland";
        mainModel.getWeatherByKey(new CityKey(city));
        verify(weatherProvider, times(1)).getWeatherByCity(eq(city),ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getWeatherByZipCalledWeatherProviderTest() throws Exception {
        String zip = "Portland";
        mainModel.getWeatherByKey(new ZipKey(zip));
        verify(weatherProvider, times(1)).getWeatherByZip(eq(zip),ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getWeatherByLocationCalledWeatherProviderTest() throws Exception {
        MyLocation location = new MyLocation(10,20);
        mainModel.getWeatherByKey(new LocationKey(location));
        verify(weatherProvider, times(1)).getWeatherByLatLon(eq(location),ArgumentMatchers.<Consumer<OpenWeatherForecast>>any());
    }

    @Test
    public void getRecentItemsIsCalledTest() throws Exception {
        Consumer<List<RecentItem>> other = mock(Consumer.class);
        mainModel.getRecentItems(other);
        verify(recentProvider, times(1)).getRecentItems();
    }


    @Test
    public void weatherPresenterOnWeatherReceivedCallbackCalledWhenWeatherRequestedByCity() throws Exception {
        CityKey cityKey = new CityKey("Portland");
        final OpenWeatherForecast o = mock(OpenWeatherForecast.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onSuccess(o);
                return null;
            }
        }).when(weatherProvider).getWeatherByCity(anyString(),ArgumentMatchers.<Consumer>any());
        mainModel.getWeatherByKey(cityKey);
        verify(weatherPresenterCallback, times(1)).onWeatherReceived(ArgumentMatchers.<WeatherForecast>any());
    }
    @Test
    public void weatherPresenterOnWeatherReceiveErrorCallbackCalledWhenWeatherRequestedByCity() throws Exception {
        CityKey cityKey = new CityKey("Portland");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onFailed("");
                return null;
            }
        }).when(weatherProvider).getWeatherByCity(anyString(),ArgumentMatchers.<Consumer>any());
        mainModel.getWeatherByKey(cityKey);
        verify(weatherPresenterCallback, times(1)).onWeatherReceiveError(anyString());
    }


    @Test
    public void weatherPresenterOnWeatherReceivedCallbackCalledWhenWeatherRequestedByZip() throws Exception {
        ZipKey zipKey = new ZipKey("97209");
        final OpenWeatherForecast o = mock(OpenWeatherForecast.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onSuccess(o);
                return null;
            }
        }).when(weatherProvider).getWeatherByZip(anyString(),ArgumentMatchers.<Consumer>any());
        mainModel.getWeatherByKey(zipKey);
        verify(weatherPresenterCallback, times(1)).onWeatherReceived(ArgumentMatchers.<WeatherForecast>any());
    }

    @Test
    public void weatherPresenterOnWeatherReceiveErrorCallbackCalledWhenWeatherRequestedByZip() throws Exception {
        ZipKey zipKey = new ZipKey("97209");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Consumer<OpenWeatherForecast>) invocation.getArguments()[1]).onFailed("");
                return null;
            }
        }).when(weatherProvider).getWeatherByZip(anyString(),ArgumentMatchers.<Consumer>any());
        mainModel.getWeatherByKey(zipKey);
        verify(weatherPresenterCallback, times(1)).onWeatherReceiveError(anyString());
    }
}