package com.opencharge.opencharge.domain.parsers.impl;

import android.util.Log;

import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.parsers.ServiceParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public class FirebaseServiceParser extends AbstractParser implements ServiceParser {

    public static final String DAY_KEY = "day";
    public static final String START_HOUR_KEY = "startHour";
    public static final String END_HOUR_KEY = "endHour";

    private static final String DAY_FORMAT = "dd/MM/yyyy";
    private static final String HOUR_FORMAT = "H:m";

    @Override
    public Service parseFromMap(String key, Map<String, Object> map) throws ParseException {
        Service service = new Service(key);

        DateFormat dayFormatter = new SimpleDateFormat(DAY_FORMAT);
        Date day = dayFormatter.parse((String)map.get(DAY_KEY));

        DateFormat hourFormatter = new SimpleDateFormat(HOUR_FORMAT);
        Date startHour = hourFormatter.parse((String)map.get(START_HOUR_KEY));
        Date endHour = hourFormatter.parse((String)map.get(END_HOUR_KEY));

        service.setDay(day);
        service.setStartHour(startHour);
        service.setEndHour(endHour);

        return service;
    }

    @Override
    public Map<String, Object> serializeService(Service service) {
        String day = new SimpleDateFormat(DAY_FORMAT).format(service.getDay());
        String startHour = new SimpleDateFormat(HOUR_FORMAT).format(service.getStartHour());
        String endHour = new SimpleDateFormat(HOUR_FORMAT).format(service.getEndHour());

        HashMap<String, Object> result = new HashMap<>();
        result.put(DAY_KEY, day);
        result.put(START_HOUR_KEY, startHour);
        result.put(END_HOUR_KEY, endHour);

        return result;
    }
}
