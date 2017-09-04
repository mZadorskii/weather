package com.sardox.weatherapp;

import com.sardox.weatherapp.model.Providers.WeatherProvider.WeatherEntities.Sys;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void printing() throws Exception {
        float a = 45.23456f;
        float b = 45.00f;

        System.out.println(a);
        System.out.println(b);

        System.out.println((int) a);
        System.out.println((int) b);

        String aa = String.valueOf(a);
        String bb = String.valueOf(b);

        System.out.println(aa);
        System.out.println(bb);

        System.out.println("DecimalFormat");
        DecimalFormat df = new DecimalFormat("#");
        String aaa = df.format(a);
        System.out.println(aaa);
    }
}