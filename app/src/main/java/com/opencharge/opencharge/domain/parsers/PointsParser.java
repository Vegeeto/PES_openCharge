package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Points;

import java.util.Map;

/**
 * Created by ferran on 15/4/17.
 */

public interface PointsParser {
    public Points parseFromMap(String key, Map<String, Object> map);
}
