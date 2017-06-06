package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Reserve;

import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public interface ReserveParser {
    Reserve parseFromMap(String key, Map<String, Object> map);
    Map<String, Object> serializeReserve(Reserve reserve);

}
