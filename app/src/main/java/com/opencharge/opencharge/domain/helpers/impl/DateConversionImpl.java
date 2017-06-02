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
    public Date StringWithLongToDate(String time) {
        return longToDate(Long.parseLong(time));
    }

    @Override
    public String ConvertLongToDateFormat(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(time);
    }

    @Override
    public Date ConvertStringToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date convertedTime = dateFormat.parse(dateString);
            return convertedTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String ConvertIntToTimeString(int hour, int minute) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date date = new Date();
        date.setHours(hour);
        date.setMinutes(minute);
        return dateFormat.format(date);
    }

    @Override
    public String ConvertDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

}
