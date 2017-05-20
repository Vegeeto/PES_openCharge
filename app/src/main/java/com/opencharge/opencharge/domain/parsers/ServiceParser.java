package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public interface ServiceParser {
    Service parseFromMap(String key, Map<String, Object> map) throws ParseException;
    Map<String, Object>  serializeService(Service service);
    String serializeServiceHour(Date hour);
    String serializeServiceDay(Date day);
}
