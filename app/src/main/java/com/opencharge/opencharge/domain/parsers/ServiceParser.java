package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Service;

import java.util.Map;

/**
 * Created by Oriol on 17/5/2017.
 */

public interface ServiceParser {
    Service parseFromMap(String key, Map<String, Object> map);
}
