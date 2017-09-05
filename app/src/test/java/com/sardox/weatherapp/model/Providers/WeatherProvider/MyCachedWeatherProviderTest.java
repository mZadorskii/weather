package com.sardox.weatherapp.model.Providers.WeatherProvider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sardox.weatherapp.model.Providers.LocationProvider.MyLocation;
import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.OpenWeatherForecast;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.CityKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.LocationKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;
import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.ZipKey;
import com.sardox.weatherapp.utils.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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

    private MyCachedWeatherProvider myCachedWeatherProvider;

    @Before
    public void setup() {
        weatherCache = CacheBuilder.newBuilder()
                .maximumSize(100)                        // maximum number of entries
                .expireAfterWrite(10, TimeUnit.MINUTES)  //// 10 min TTL  items will stay in cache for 10 min
                .build();

        MockitoAnnotations.initMocks(this);
        myCachedWeatherProvider = new MyCachedWeatherProvider(openWeatherService, weatherCache);
    }

    @Test
    /*
    1. create mock of Provider
    2. request weather by zip
    3. make sure result is received ???
    4. make sure cache has this item
     */
    public void getWeatherByZipInsertedToCacheTest() {
        String openWeatherForecastName = "test_forecast";
        String zipInput = "97200";
        ZipKey zipkey = new ZipKey(zipInput);

        OpenWeatherForecast o = new OpenWeatherForecast();
        o.setName(openWeatherForecastName);

        Consumer<OpenWeatherForecast> other = mock(Consumer.class);
        final Response<OpenWeatherForecast> resp = Response.success(o);
        final Call<OpenWeatherForecast> call = mock(Call.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<OpenWeatherForecast>) invocation.getArguments()[0]).onResponse(call, resp);
                return null;
            }
        }).when(call).enqueue(ArgumentMatchers.<Callback>any());


        when(openWeatherService.getWeatherByZIP(ArgumentMatchers.<String>any(), ArgumentMatchers.<String>any())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByZip(zipkey.getZip(), other);

        assertNotNull(weatherCache.getIfPresent(zipkey));
        assertEquals(weatherCache.getIfPresent(zipkey).getName(), openWeatherForecastName);
    }

    @Test
    public void getWeatherByZipWasCalledInWeatherService() {
        Consumer<OpenWeatherForecast> other = mock(Consumer.class);
        Call<OpenWeatherForecast> call = mock(Call.class);

        when(openWeatherService.getWeatherByZIP(ArgumentMatchers.<String>any(), ArgumentMatchers.<String>any())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByZip("", other);
        verify(openWeatherService.getWeatherByZIP(anyString(), anyString()), times(1)).enqueue(ArgumentMatchers.<Callback>any());
    }


    @Test
    public void getWeatherByCityInsertedToCacheTest() {
        String openWeatherForecastName = "test_forecast";
        String cityInput = "Portland";
        CityKey cityKey = new CityKey(cityInput);

        OpenWeatherForecast o = new OpenWeatherForecast();
        o.setName(openWeatherForecastName);

        Consumer<OpenWeatherForecast> other = mock(Consumer.class);
        final Response<OpenWeatherForecast> resp = Response.success(o);
        final Call<OpenWeatherForecast> call = mock(Call.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<OpenWeatherForecast>) invocation.getArguments()[0]).onResponse(call, resp);
                return null;
            }
        }).when(call).enqueue(ArgumentMatchers.<Callback>any());


        when(openWeatherService.getWeatherByCity(ArgumentMatchers.<String>any(), ArgumentMatchers.<String>any())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByCity(cityKey.getCity(), other);

        assertNotNull(weatherCache.getIfPresent(cityKey));
        assertEquals(weatherCache.getIfPresent(cityKey).getName(), openWeatherForecastName);
    }

    @Test
    public void getWeatherByCityWasCalledInWeatherService() {
        Consumer<OpenWeatherForecast> other = mock(Consumer.class);
        Call<OpenWeatherForecast> call = mock(Call.class);

        when(openWeatherService.getWeatherByCity(ArgumentMatchers.<String>any(), ArgumentMatchers.<String>any())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByCity("", other);
        verify(openWeatherService.getWeatherByCity(anyString(), anyString()), times(1)).enqueue(ArgumentMatchers.<Callback>any());
    }

    @Test
    public void getWeatherByLatInsertedToCacheTest() {
        String openWeatherForecastName = "test_forecast";
        MyLocation location = new MyLocation(10, 20);
        LocationKey locationKey = new LocationKey(location);

        OpenWeatherForecast o = new OpenWeatherForecast();
        o.setName(openWeatherForecastName);

        Consumer<OpenWeatherForecast> other = mock(Consumer.class);
        final Response<OpenWeatherForecast> resp = Response.success(o);
        final Call<OpenWeatherForecast> call = mock(Call.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<OpenWeatherForecast>) invocation.getArguments()[0]).onResponse(call, resp);
                return null;
            }
        }).when(call).enqueue(ArgumentMatchers.<Callback>any());


        when(openWeatherService.getWeatherLatLon(anyDouble(), anyDouble(), ArgumentMatchers.<String>any())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByLatLon(locationKey.getLocation(), other);

        assertNotNull(weatherCache.getIfPresent(locationKey));
        assertEquals(weatherCache.getIfPresent(locationKey).getName(), openWeatherForecastName);
    }

    @Test
    public void getWeatherByLatWasCalledInWeatherService() {
        Call<OpenWeatherForecast> call = mock(Call.class);

        when(openWeatherService.getWeatherLatLon(anyDouble(), anyDouble(), anyString())).thenReturn(call);
        myCachedWeatherProvider.getWeatherByLatLon(new MyLocation(0, 0), null);
        verify(openWeatherService.getWeatherLatLon(anyDouble(), anyDouble(), anyString()), times(1)).enqueue(ArgumentMatchers.<Callback>any());
    }
}