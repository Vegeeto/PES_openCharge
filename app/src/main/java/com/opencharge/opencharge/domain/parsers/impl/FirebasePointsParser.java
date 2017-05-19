package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.parsers.PointsParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ferran on 15/4/17.
 */

public class FirebasePointsParser implements PointsParser {
    public static final double COORDINATES_PRECISION = 0.0001;

    public static final String TOWN_KEY = "town";
    public static final String STREET_KEY = "street";
    public static final String NUMBER_KEY = "number";
    public static final String LON_KEY = "lon";
    public static final String LAT_KEY = "lat";
    public static final String ACCESS_TYPE_KEY = "accessType";
    public static final String CONNECTOR_TYPE_LIST_KEY = "connectorTypeList";
    public static final String SCHEDULE_KEY = "schedule";

    @Override
    public Point parseFromMap(String key, Map<String, Object> map) {
        Point point = new Point(key);

        point.setAccessType(parseAccessTypeFromMap(map));
        point.setConnectorTypeList(parseConnectorTypeFromMap(map));

        point.setLat(parseDoubleKeyFromMap(LAT_KEY, map));
        point.setLon(parseDoubleKeyFromMap(LON_KEY, map));

        point.setTown(parseStringKeyFromMap(TOWN_KEY, map));
        point.setStreet(parseStringKeyFromMap(STREET_KEY, map));
        point.setNumber(parseStringKeyFromMap(NUMBER_KEY, map));

        point.setSchedule(parseStringKeyFromMap(SCHEDULE_KEY, map));

        return point;
    }

    private @Point.AccessType String parseAccessTypeFromMap(Map<String, Object> map) {
        @Point.AccessType String accessType = (String)map.get(ACCESS_TYPE_KEY);
        if (!isCorrectAccessType(accessType)) {
            accessType = Point.UNKNOWN_ACCESS;
        }

        return accessType;
    }

    private @Point.ConnectorType List<String> parseConnectorTypeFromMap(Map<String, Object> map) {
        List<String> connectorTypeList = (List<String>) map.get(CONNECTOR_TYPE_LIST_KEY);
        for (@Point.ConnectorType String conn:connectorTypeList) {
            if (!isCorrectConnectorType(conn)) {
                conn = Point.UNKNOWN_CONNECTOR;
            }
        }
        return connectorTypeList;
    }

    private double parseDoubleKeyFromMap(String key, Map<String, Object> map) {
        double value = 0.0;
        if (map.containsKey(key)) {
            value = (double)map.get(key);
        }
        return value;
    }

    private String parseStringKeyFromMap(String key, Map<String, Object> map) {
        if (map.containsKey(key)) {
            return (String)map.get(key);
        }
        else {
            return null;
        }
    }

    private boolean isCorrectAccessType(String accessType) {
        String[] allowedTypes = new String[] {Point.PUBLIC_ACCESS, Point.PRIVATE_ACCESS, Point.PARTICULAR_ACCESS};
        return Arrays.asList(allowedTypes).contains(accessType);
    }

    private boolean isCorrectConnectorType(String connectorType) {
        String[] allowedTypes = new String[] {Point.SLOW_CONNECTOR, Point.FAST_CONNECTOR, Point.RAPID_CONNECTOR};
        return Arrays.asList(allowedTypes).contains(connectorType);
    }
}
