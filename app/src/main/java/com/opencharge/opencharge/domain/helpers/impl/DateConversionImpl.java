package com.opencharge.opencharge.domain.helpers.impl;

import com.opencharge.opencharge.domain.helpers.DateConversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public class DateConversionImpl implements DateConversion {

    @Override
    public long DateToLong(Date date) {
        return date.getTime();
    }

    @Override
    public Date longToDate(long time) {
        return new Date(time);
    }

    @Override
    public Date StringToDate(String time) {
        return longToDate(Long.parseLong(time));
    }

    @Override
    public Date ConvertStringToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date convertedDate = dateFormat.parse(dateString);
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date ConvertStringToTime(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            Date convertedTime = dateFormat.parse(dateString);
            return convertedTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
