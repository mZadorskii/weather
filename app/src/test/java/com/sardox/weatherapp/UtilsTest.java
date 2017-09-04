package com.sardox.weatherapp;

import com.sardox.weatherapp.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sardox on 9/3/2017.
 */
public class UtilsTest {

    @Test
    public void validateInput() throws Exception {
        final String city = "Portland";
        final String cityWithNum = "Portland2";
        final String zip = "97209";
        final String zipShort = "9729";
        final String zipLong = "97293452";
        final String empty = "";

        assertEquals(Utils.INPUT_TYPE.CITY, Utils.validateInput(city));
        assertEquals(Utils.INPUT_TYPE.ZIP, Utils.validateInput(zip));
        assertEquals(Utils.INPUT_TYPE.UNKNOWN, Utils.validateInput(empty));
        //assertEquals(Utils.INPUT_TYPE.UNKNOWN, Utils.validateInput(zipShort));    THIS SHOULD FAIL. add more rules
        //assertEquals(Utils.INPUT_TYPE.UNKNOWN, Utils.validateInput(zipLong));     THIS SHOULD FAIL. add more rules
        //assertEquals(Utils.INPUT_TYPE.UNKNOWN, Utils.validateInput(cityWithNum));  THIS SHOULD FAIL. add more rules

    }

    @Test
    public void convertTemp() throws Exception {
        final float input = 300f;
        final float hundrenKinC = 26.85f;
        final float hundrenKinF = 80.3f;

        assertEquals(hundrenKinC, Utils.convertTemp(input, Utils.TEMPERATURE.Celsius), 0.1);
        assertEquals(hundrenKinF, Utils.convertTemp(input, Utils.TEMPERATURE.Fahrenheit), 0.1);
    }

}