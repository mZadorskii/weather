package com.sardox.weatherapp.model.Providers.WeatherProvider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sardox.weatherapp.utils.Consumer;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.LocationKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created by sardox on 9/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MyCachedWeatherProviderTest {

    private Cache<MyCacheKey, OpenWeatherForecast> weatherCache;

    @Mock
    private OpenWeatherForecast weatherForecast;

    @Mock
    OpenWeatherService openWeatherService;

    @Mock
    CityKey cityKey;

    @Mock
    ZipKey zipKey;
    @Mock
    LocationKey locationKey;
    @Mock
    LocationKey locationKeyNoValue;


    private MyCachedWeatherProvider myCachedWeatherProvider;

    @Before
    public void setup(){
        weatherCache = CacheBuilder.newBuilder()
                .maximumSize(100)                        // maximum number of entries
                .expireAfterWrite(10, TimeUnit.MINUTES)  //// 10 min TTL  items will stay in cache for 10 min
                .build();

        MockitoAnnotations.initMocks(this);
        myCachedWeatherProvider = new MyCachedWeatherProvider(openWeatherService, weatherCache);
    }
    @Test
    public void testCallbackSample() {
    Consumer<OpenWeatherForecast> consumer = mock(Consumer.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(consumer).onFailed("");
        myCachedWeatherProvider.getWeatherByZip("97200", consumer);
        verify(consumer, times(1)).onFailed("");
    }



    @Mock
    private Call<OpenWeatherForecast> weatherResponseCall;
    @Captor
    ArgumentCaptor<Callback<OpenWeatherForecast>> argCaptor;

    @Test
    public void testCallbackSample2() {
        Consumer<OpenWeatherForecast> consumer = mock(Consumer.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(consumer).onFailed("");
        myCachedWeatherProvider.getWeatherByZip("97200", consumer);
        verify(consumer, times(1)).onFailed("");
    }

    @Captor
    private ArgumentCaptor<Consumer<OpenWeatherForecast>> weatherCaptor;
    private ArgumentCaptor<String> weatherCaptor2;
    private ArgumentCaptor<String> weatherCaptor3;
    @Test
    public void testCallbackSample3() {



        Mockito.verify()
        OpenWeatherForecast openWeatherForecast = new OpenWeatherForecast();
        openWeatherForecast.setBase("my base");

        String nn = "a";

        weatherCaptor.getValue().onSuccess(openWeatherForecast)
        ;
        Consumer<OpenWeatherForecast> consumer = mock(Consumer.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(consumer).onFailed("");
        myCachedWeatherProvider.getWeatherByZip("97200", consumer);
        verify(consumer, times(1)).onFailed("");
    }



    @Test
    public void cachePutAndGetByKeyTest(){
        weatherCache.put(cityKey, weatherForecast);
        weatherCache.put(zipKey, weatherForecast);
        weatherCache.put(locationKey, weatherForecast);
        assertNotNull(weatherCache.getIfPresent(cityKey));
        assertNotNull(weatherCache.getIfPresent(zipKey));
        assertNotNull(weatherCache.getIfPresent(locationKey));
        assertNull(weatherCache.getIfPresent(locationKeyNoValue));
    }

    /*
        CallbackSample<String> action1 = mock(CallbackSample.class);
        ArgumentCaptor<String> result1 = ArgumentCaptor.forClass(String.class);
        doNothing().when(action1).onObjectReceived(result1.capture());
        verify(action1, Mockito.times(1)).onObjectReceived(ArgumentMatchers.<String>any());
        testMePlz.test();
        verify(callbackSample).onObjectReceived(captor.capture());
        CallbackSample<MyLocation> myLocationCallbackSample = captor.getValue();
        myLocationCallbackSample.onObjectReceived(new MyLocation(10,20));
        assertEquals();
     */
    @Test
    /*
    1. create mock of Provider
    2. request weather by zip
    3. make sure result is received ???
    4. make sure cache has this item
     */
    public void getWeatherByCityTest() {
        String zipInput = "97200";
        ZipKey zipkey = new ZipKey(zipInput);

        myCachedWeatherProvider.getWeatherByZip(zipInput, new Consumer<OpenWeatherForecast>() {
            @Override
            public void onSuccess(OpenWeatherForecast var1) {

            }

            @Override
            public void onFailed(String error) {

            }
        });


        //4
        assertNotNull(weatherCache.getIfPresent(zipkey));
    }


}