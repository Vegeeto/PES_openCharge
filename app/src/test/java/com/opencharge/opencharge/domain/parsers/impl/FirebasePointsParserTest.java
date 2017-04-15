package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Points;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        map.put(FirebasePointsParser.LON_KEY, 3.22);
        map.put(FirebasePointsParser.LAT_KEY, 2.33);
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Points.PUBLIC_ACCESS);
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Points.SLOW_CONNECTOR);
        map.put(FirebasePointsParser.SHEDULE_KEY, "some shedule");
    }

    //<editor-fold desc="Id tests">
    @Test
    public void test_parseFromMap_createPointWithCorrectId() {
        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Point id not parsed", key, p.getId());
    }
    //</editor-fold>

    //<editor-fold desc="AccessType tests">
    @Test
    public void testMapWithPublicAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Points.PUBLIC_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithPrivateAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Points.PRIVATE_ACCESS);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Points.PRIVATE_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithIndividualAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Points.INDIVIDUAL_ACCESS);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Points.INDIVIDUAL_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithUnknownAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, Points.UNKNOWN_ACCESS);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Points.UNKNOWN_ACCESS, p.getAccessType());
    }

    @Test
    public void testMapWithWrongAccessType_parseFromMap_createPointWithCorrectAccessType() {
        //Given
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, "Wrong access type");

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed accessType", Points.UNKNOWN_ACCESS, p.getAccessType());
    }
    //</editor-fold>

    //<editor-fold desc="ConnectorType tests">
    @Test
    public void testMapWithSlowConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Points.SLOW_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithFastConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Points.FAST_CONNECTOR);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Points.FAST_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithRapidConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Points.RAPID_CONNECTOR);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Points.RAPID_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithUnknownConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, Points.UNKNOWN_CONNECTOR);

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Points.UNKNOWN_CONNECTOR, p.getConnectorType());
    }

    @Test
    public void testMapWithWrongConnector_parseFromMap_createPointWithCorrectConnectorType() {
        //Given
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, "Wrong connectorType");

        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed connectorType", Points.UNKNOWN_CONNECTOR, p.getConnectorType());
    }
    //</editor-fold>
}
