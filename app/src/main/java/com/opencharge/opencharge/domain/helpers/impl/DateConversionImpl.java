package com.opencharge.opencharge.domain.helpers.impl;

import com.opencharge.opencharge.domain.helpers.DateConversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public class DateConversionImpl implements DateConversion {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    public DateConversionImpl() {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_FORMAT);
    }

    //<editor-fold desc="String formats to Date">

    @Override
    public Date ConvertStringToDate(String dateString){
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
        try {
            Date convertedTime = timeFormat.parse(dateString);
            return convertedTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Object to String">
    @Override
    public String ConvertLongToString(long time) {
        return dateFormat.format(time);
    }

    @Override
    public String ConvertDateToString(Date date) {
        return dateFormat.format(date);
    }

    @Override
    public String ConvertTimeToString(Date date) {
        return timeFormat.format(date);
    }

    @Override
    public String ConvertHourAndMinutesToString(int hour, int minute) {
        Date date = new Date();
        date.setHours(hour);
        date.setMinutes(minute);
        return timeFormat.format(date);
    }
    //</editor-fold>



}
