package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Points;
import com.opencharge.opencharge.domain.parsers.PointsParser;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by ferran on 15/4/17.
 */

public class FirebasePointsParser implements PointsParser {
    public static final String TOWN_KEY = "town";
    public static final String STREET_KEY = "street";
    public static final String NUMBER_KEY = "number";
    public static final String LON_KEY = "lon";
    public static final String LAT_KEY = "lat";
    public static final String ACCESS_TYPE_KEY = "accessType";
    public static final String CONNECTOR_TYPE_KEY = "connectorType";
    public static final String SHEDULE_KEY = "schedule";

    @Override
    public Points parseFromMap(String key, Map<String, Object> map) {
        Points point = new Points(key);
        point.setAccessType(parseAccessTypeFromMap(map));
        point.setConnectorType(parseConnectorTypeFromMap(map));
        return point;
    }

    private @Points.AccessType String parseAccessTypeFromMap(Map<String, Object> map) {
        @Points.AccessType String accessType = (String)map.get(ACCESS_TYPE_KEY);
        if (!isCorrectAccessType(accessType)) {
            accessType = Points.UNKNOWN_ACCESS;
        }

        return accessType;
    }

    private boolean isCorrectAccessType(String accessType) {
        String[] allowedTypes = new String[] {Points.PUBLIC_ACCESS, Points.PRIVATE_ACCESS, Points.INDIVIDUAL_ACCESS};
        return Arrays.asList(allowedTypes).contains(accessType);
    }

    private @Points.ConnectorType String parseConnectorTypeFromMap(Map<String, Object> map) {
        @Points.ConnectorType String connectorType = (String)map.get(CONNECTOR_TYPE_KEY);
        if (!isCorrectConnectorType(connectorType)) {
            connectorType = Points.UNKNOWN_CONNECTOR;
        }

        return connectorType;
    }

    private boolean isCorrectConnectorType(String connectorType) {
        String[] allowedTypes = new String[] {Points.SLOW_CONNECTOR, Points.FAST_CONNECTOR, Points.RAPID_CONNECTOR};
        return Arrays.asList(allowedTypes).contains(connectorType);
    }
}
