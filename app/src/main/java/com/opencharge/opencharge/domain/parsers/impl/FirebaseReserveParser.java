package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;
import com.opencharge.opencharge.domain.parsers.ServiceParser;

import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public class FirebaseReserveParser extends AbstractParser implements ReserveParser {

    public static final String DAY_KEY = "day";
    public static final String START_HOUR_KEY = "startHour";
    public static final String END_HOUR_KEY = "endHour";

    //TODO: finish this class => ORIOL

    @Override
    public Reserve parseFromMap(String key, Map<String, Object> map) {
        Reserve reserve = new Reserve(key);

        reserve.setDay(parseDateKeyFromMap(DAY_KEY, map));
        reserve.setStartHour(parseDateKeyFromMap(START_HOUR_KEY, map));
        reserve.setEndHour(parseDateKeyFromMap(END_HOUR_KEY, map));

        return reserve;
    }

}
