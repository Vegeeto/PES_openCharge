package com.opencharge.opencharge.domain.helpers.impl;

import com.opencharge.opencharge.domain.helpers.DateConversion;

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

}
