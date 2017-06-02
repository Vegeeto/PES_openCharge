package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Service;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ferran on 3/6/17.
 */

public class FirebaseServiceParserTest {
    FirebaseServiceParser sut;

    //Collaborators
    Map<String, Object> map;
    Service service;
    String key;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new FirebaseServiceParser();
    }

    private void setUpCollaborators() {
        key = "point1";

        map = new HashMap<>();
        map.put(FirebaseServiceParser.DAY_KEY, "28/02/2017");
        map.put(FirebaseServiceParser.START_HOUR_KEY, "1:59");
        map.put(FirebaseServiceParser.END_HOUR_KEY, "23:2");

        service = new Service(key);
        service.setDay(new Date("02/28/2017"));
        service.setStartHour(new Date("01/01/1970 01:59"));
        service.setEndHour(new Date("01/01/1970 23:02"));
    }

    //<editor-fold desc="Parse tests">
    @Test
    public void test_parseFromMap_createServiceWithCorrectParams() throws ParseException {
        //When
        Service parsedService = sut.parseFromMap(key, map);

        //Then
        assertEquals("Service id not parsed", key, parsedService.getId());
        assertEquals("Service day not parsed", service.getDay(), parsedService.getDay());
        assertEquals("Service startHour not parsed", service.getStartHour(), parsedService.getStartHour());
        assertEquals("Service endHour not parsed", service.getEndHour(), parsedService.getEndHour());
    }

    @Test(expected=ParseException.class)
    public void testMapWithWrongDay_parseFromMap_throwParseException() throws ParseException {
        //Given
        map.put(FirebaseServiceParser.DAY_KEY, "2qweqwe8/02qwe/2017");

        //When
        Service p = sut.parseFromMap(key, map);
    }

    @Test(expected=ParseException.class)
    public void testMapWithWrongStartHour_parseFromMap_throwParseException() throws ParseException {
        //Given
        map.put(FirebaseServiceParser.START_HOUR_KEY, "01wefq:59");

        //When
        Service p = sut.parseFromMap(key, map);
    }

    @Test(expected=ParseException.class)
    public void testMapWithWrongEndHour_parseFromMap_throwParseException() throws ParseException {
        //Given
        map.put(FirebaseServiceParser.END_HOUR_KEY, "01wefq:59");

        //When
        Service p = sut.parseFromMap(key, map);
    }
    //</editor-fold>

    @Test
    public void test_serializeService_createMapWithCorrectParams() {
        //When
        Map<String, Object> serializedMap = sut.serializeService(service);

        //Then
        assertEquals("Map day not serialized", map.get(FirebaseServiceParser.DAY_KEY), serializedMap.get(FirebaseServiceParser.DAY_KEY));
        assertEquals("Map startHour not serialized", map.get(FirebaseServiceParser.START_HOUR_KEY), serializedMap.get(FirebaseServiceParser.START_HOUR_KEY));
        assertEquals("Map endHour not serialized", map.get(FirebaseServiceParser.END_HOUR_KEY), serializedMap.get(FirebaseServiceParser.END_HOUR_KEY));
    }
}
