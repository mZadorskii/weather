package com.sardox.weatherapp.utils;


import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sardox on 9/2/2017.
 */

public class Utils {
    static String postalRegex = "^[0-9]{5}(?:-[0-9]{4})?$";

    /**
     * here only basic rules
     need to add more rules for incorrect inputs
     */
    public static INPUT_TYPE validateInput(String input) {
        Pattern pattern = Pattern.compile(postalRegex);
        Matcher matcher = pattern.matcher(input);
        if (input.length() < 3) return INPUT_TYPE.UNKNOWN;
        if (matcher.matches()) return INPUT_TYPE.ZIP;
        else return INPUT_TYPE.CITY;

    }

    public static float convertTemp(float kelvin, TEMPERATURE desiredUnit) {
        switch (desiredUnit) {
            case Celsius:
                return kelvin - 273.15F;
            case Fahrenheit:
                return kelvin * 1.8F - 459.67F;
            default: {
                return kelvin;
            }
        }
    }

    public enum INPUT_TYPE {
        CITY,
        ZIP,
        UNKNOWN,
    }

    public enum TEMPERATURE {
        Celsius,
        Fahrenheit,
    }
}
