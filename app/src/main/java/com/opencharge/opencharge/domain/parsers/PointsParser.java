package com.opencharge.opencharge.domain.parsers;

import com.opencharge.opencharge.domain.Entities.Point;

import java.util.Map;

/**
 * Created by ferran on 15/4/17.
 */

public interface PointsParser {
    Point parseFromMap(String key, Map<String, Object> map);
    Map<String, Object> serializePoint(Point point);
}
