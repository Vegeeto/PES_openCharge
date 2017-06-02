package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.parsers.Parser;

import java.util.Date;
import java.util.Map;

/**
 * Created by Oriol on 4/5/2017.
 */

public abstract class AbstractParser implements Parser {

    public double parseDoubleKeyFromMap(String key, Map<String, Object> map) {
        double value = 0.0;
        if (map.containsKey(key)) {
            value = (double)map.get(key);
        }
        return value;
    }

    public String parseStringKeyFromMap(String key, Map<String, Object> map) {
        if (map.containsKey(key)) {
            return (String)map.get(key);
        }
        else {
            return null;
        }
    }

    public Date parseDateLongKeyFromMap(String key, Map<String, Object> map) {
        String date = "";
        if (map.containsKey(key)) {
            date = (String)map.get(key);
        }
        DateConversion dateConversion = new DateConversionImpl();
        return dateConversion.StringWithLongToDate(date);
    }

    public Date parseDateStringKeyFromMap(String key, Map<String, Object> map) {
        String date = "";
        if (map.containsKey(key)) {
            date = (String)map.get(key);
        }
        DateConversion dateConversion = new DateConversionImpl();
        return dateConversion.ConvertStringToDate(date);
    }

    public Date parseTimeKeyFromMap(String key, Map<String, Object> map) {
        String time = "";
        if (map.containsKey(key)) {
            time = (String)map.get(key);
        }
        DateConversion dateConversion = new DateConversionImpl();
        return dateConversion.ConvertStringToTime(time);
    }


    public boolean parseBooleanKeyFromMap(String key, Map<String, Object> map) {
        boolean value = false;
        if (map.containsKey(key)) {
            value = (boolean)map.get(key);
        }
        return value;
    }
}
