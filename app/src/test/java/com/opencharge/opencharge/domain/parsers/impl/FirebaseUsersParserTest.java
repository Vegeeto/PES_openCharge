package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ferran on 3/6/17.
 */

public class FirebaseUsersParserTest {
    FirebaseUsersParser sut;

    //Collaborators
    Map<String, Object> map;
    User user;
    String key;

    //<editor-fold desc="SetUp tests">
    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new FirebaseUsersParser();
    }

    private void setUpCollaborators() {
        key = "userId";
        setUpMapForTests();
        setUpUserForTests();
    }

    private void setUpMapForTests() {
        map = new HashMap<>();
        map.put(FirebaseUsersParser.USERNAME_KEY , "user name");
        map.put(FirebaseUsersParser.PHOTO_KEY , "path to photo");
        map.put(FirebaseUsersParser.EMAIL_KEY , "user@email.com");
        map.put(FirebaseUsersParser.MINUTES_KEY , Long.valueOf(10));

        List<Map<String, String>> pointsList = createPointsListForMapTest();
        map.put(FirebaseUsersParser.POINTS_KEY, pointsList);
    }

    private List<Map<String, String>> createPointsListForMapTest() {
        Map<String, String> point1 = new HashMap<>();
        point1.put(FirebaseUsersParser.POINT_ID, "Point1ID");
        point1.put(FirebaseUsersParser.POINT_ADDRESS, "Point1 Address");

        Map<String, String> point2 = new HashMap<>();
        point2.put(FirebaseUsersParser.POINT_ID, "Point2ID");
        point2.put(FirebaseUsersParser.POINT_ADDRESS, "Point2 Address");

        List<Map<String, String>> pointsList = new ArrayList<>();
        pointsList.add(point1);
        pointsList.add(point2);

        return pointsList;
    }

    private void setUpUserForTests() {
        user = new User();
        user.setId(key);
        user.setEmail("user@email.com");
        user.setUsername("user name");
        user.setPhoto("path to photo");
        user.setMinutes(10);

        List<UserPointSummary> points = createPointsListForUserTest();
        user.setPoints(points);
    }

    private List<UserPointSummary> createPointsListForUserTest() {
        UserPointSummary point1 = new UserPointSummary("Point1ID", "Point1 Address");
        UserPointSummary point2 = new UserPointSummary("Point2ID", "Point2 Address");
        ArrayList<UserPointSummary> list = new ArrayList<>();
        Collections.addAll(list, point1, point2);
        return list;
    }
    //</editor-fold>

    //<editor-fold desc="Parse tests">
    @Test
    public void test_parseFromMap_createUserWithCorrectParams() {
        //When
        User parsedUser = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed Id", key, parsedUser.getId());
        assertEquals("Wrong parsed UserName", user.getUsername(), parsedUser.getUsername());
        assertEquals("Wrong parsed Email", user.getEmail(), parsedUser.getEmail());
        assertEquals("Wrong parsed Photo", user.getPhoto(), parsedUser.getPhoto());
        assertEquals("Wrong parsed Minutes", user.getMinutes(), parsedUser.getMinutes());
        assertEquals("Wrong parsed Points List", user.getPoints(), parsedUser.getPoints());

    }
    //</editor-fold>
}
