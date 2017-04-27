package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Point;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ferran on 15/4/17.
 */

public class FirebasePointsParserTest {
    FirebasePointsParser sut;

    //Collaborators
    Map<String, Object> map;
    String key;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new FirebasePointsParser();
    }

    private void setUpCollaborators() {
        key = "point1";

        map = new HashMap<>();
        map.put(FirebasePointsParser.TOWN_KEY, "barcelona");
        map.put(FirebasePointsParser.STREET_KEY, "diagonal");
        map.put(FirebasePointsParser.NUMBER_KEY, "321-322");
        map.put(FirebasePointsParser.LON_KEY, 3.2222);
        map.put(FirebasePointsParser.LAT_KEY, 2.3333);
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Point.PUBLIC_ACCESS);
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Point.SLOW_CONNECTOR);
        map.put(FirebasePointsParser.SCHEDULE_KEY, "some schedule");
    }

    //<editor-fold desc="Id tests">
    @Test
    public void test_parseFromMap_createPointWithCorrectId() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Point id not parsed", key, p.getId());
    }
    //</editor-fold>

    //<editor-fold desc="AccessType tests">
    @Test
    public void testMapWithPublicAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Point.PUBLIC_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithPrivateAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Point.PRIVATE_ACCESS);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Point.PRIVATE_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithIndividualAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Point.PARTICULAR_ACCESS);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Point.PARTICULAR_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithUnknownAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Point.UNKNOWN_ACCESS);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Point.UNKNOWN_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithWrongAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, "Wrong access type");

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Point.UNKNOWN_ACCESS, p.getAccessType());
    }
    //</editor-fold>

    //<editor-fold desc="ConnectorType tests">
    @Test
    public void testMapWithSlowConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Point.SLOW_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithFastConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Point.FAST_CONNECTOR);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Point.FAST_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithRapidConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Point.RAPID_CONNECTOR);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Point.RAPID_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithUnknownConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Point.UNKNOWN_CONNECTOR);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Point.UNKNOWN_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithWrongConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, "Wrong connectorType");

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Point.UNKNOWN_CONNECTOR, p.getConnectorType());
    }
    //</editor-fold>

    //<editor-fold desc="Lat&Lon tests">
    @Test
    public void testMapWithLatAndLon_parseFromMap_createPointWithCorrectConnectorType() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed lat", 2.3333, p.getLatCoord(), FirebasePointsParser.COORDINATES_PRECISION);
        assertEquals("Wrong parsed lon", 3.2222, p.getLonCoord(), FirebasePointsParser.COORDINATES_PRECISION);
    }

    @Test
    public void testMapWithoutLatAndLon_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.remove(FirebasePointsParser.LAT_KEY);
        map.remove(FirebasePointsParser.LON_KEY);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed lat", 0.0000, p.getLatCoord(), FirebasePointsParser.COORDINATES_PRECISION);
        assertEquals("Wrong parsed lon", 0.0000, p.getLonCoord(), FirebasePointsParser.COORDINATES_PRECISION);
    }
    //</editor-fold>

    //<editor-fold desc="Address params tests">
    @Test
    public void testMapWithAddressParams_parseFromMap_createPointWithCorrectConnectorType() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed town", "barcelona", p.getTown());
        assertEquals("Wrong parsed street", "diagonal", p.getStreet());
        assertEquals("Wrong parsed number", "321-322", p.getNumber());
    }

    @Test
    public void testMapWithoutAddressParams_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.remove(FirebasePointsParser.TOWN_KEY);
        map.remove(FirebasePointsParser.STREET_KEY);
        map.remove(FirebasePointsParser.NUMBER_KEY);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertNull("Wrong parsed town", p.getTown());
        assertNull("Wrong parsed street", p.getStreet());
        assertNull("Wrong parsed number", p.getNumber());
    }
    //</editor-fold>

    //<editor-fold desc="Shedule tests">
    @Test
    public void testMapWithSchedule_parseFromMap_createPointWithCorrectConnectorType() {
        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed schedule", "some schedule", p.getSchedule());
    }

    @Test
    public void testMapWithoutSchedule_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.remove(FirebasePointsParser.SCHEDULE_KEY);

        //When
        Point p = sut.parseFromMap(key, map);

        //Then
        assertNull("Wrong parsed schedule", p.getSchedule());
    }
    //</editor-fold>
}
