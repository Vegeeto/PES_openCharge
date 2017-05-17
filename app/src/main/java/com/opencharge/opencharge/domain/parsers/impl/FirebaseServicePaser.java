package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.parsers.ServiceParser;

import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public class FirebaseServicePaser extends AbstractParser implements ServiceParser {

    public static final String DAY_KEY = "day";
    public static final String START_HOUR_KEY = "startHour";
    public static final String END_HOUR_KEY = "endHour";

    @Override
    public Service parseFromMap(String key, Map<String, Object> map) {
        Service service = new Service(key);

        service.setDay(parseDateKeyFromMap(DAY_KEY, map));
        service.setStartHour(parseDateKeyFromMap(START_HOUR_KEY, map));
        service.setEndHour(parseDateKeyFromMap(END_HOUR_KEY, map));

        return service;
    }

}
