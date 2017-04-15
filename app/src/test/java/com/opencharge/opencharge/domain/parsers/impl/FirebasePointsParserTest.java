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
        map.put(FirebasePointsParser.ACCESS_TYPE_KEY, "public");
        map.put(FirebasePointsParser.CONNECTOR_TYPE_KEY, "slow");
        map.put(FirebasePointsParser.SHEDULE_KEY, "some shedule");
    }

    @Test
    public void test_parseFromMap_createPointWithCorrectId() {
        //When
        Points p = sut.parseFromMap(key, map);

        //Then
        assertEquals("Point id not parsed", key, p.getId());
    }
}
