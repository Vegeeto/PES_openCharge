package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.parsers.UsersParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DmnT on 18/05/2017.
 */

public class FirebaseUsersParser implements UsersParser {

    public static final String USERNAME_KEY = "username";
    public static final String PHOTO_KEY = "photo";
    public static final String EMAIL_KEY = "email";
    public static final String MINUTES_KEY = "minutes";
    public static final String POINTS_KEY = "points";
    public static final String ACCESS_KEY = "accessType";
    public static final String POINT_ID = "id";
    public static final String POINT_ADDRESS = "address";

    @Override
    public User parseFromMap(String key, Map<String, Object> map) {
        User user = new User(key);
        user.setUsername(parseStringKeyFromMap(USERNAME_KEY, map));
        user.setPhoto(parseStringKeyFromMap(PHOTO_KEY, map));
        user.setEmail(parseStringKeyFromMap(EMAIL_KEY, map));
        user.setMinutes(parseLongKeyFromMap(MINUTES_KEY, map).intValue());
        user.setPoints(parseArrayListFromMap(POINTS_KEY, map));

        return user;
    }

    @Override
    public Map<String, Object> serializeUser(User user) {
        Map<String, Object> serializedUser = new HashMap<>();

        serializedUser.put(USERNAME_KEY, user.getUsername());
        serializedUser.put(EMAIL_KEY, user.getEmail());
        serializedUser.put(PHOTO_KEY, user.getPhoto());

        Long minutes = Long.valueOf(user.getMinutes());
        serializedUser.put(MINUTES_KEY, minutes);

        List<Map<String, String>> serializedPoints = serializePointsFromUser(user);
        serializedUser.put(POINTS_KEY, serializedPoints);

        return serializedUser;
    }

    private List<Map<String, String>> serializePointsFromUser(User user) {
        List<Map<String, String>> serializedPoints = new ArrayList<>();

        for (UserPointSummary point : user.getPoints()) {
            Map<String, String> serializedPoint = serializePointFromUserPointSummary(point);
            serializedPoints.add(serializedPoint);
        }

        return serializedPoints;
    }

    private Map<String, String> serializePointFromUserPointSummary(UserPointSummary point) {
        Map<String, String> serializedPoint = new HashMap<>();

        serializedPoint.put(POINT_ID, point.getPointId());
        serializedPoint.put(POINT_ADDRESS, point.getPointAddress());

        return serializedPoint;
    }

    private ArrayList<UserPointSummary> parseArrayListFromMap(String key, Map<String, Object> map) {
        ArrayList<UserPointSummary> arrayList = new ArrayList<>();
        if (map.containsKey(key)) {

            List<HashMap<String, String>> totsPunts = (List<HashMap<String, String>>) map.get(key);
            for (HashMap<String, String> element : totsPunts) {
                String idPunt = element.get(POINT_ID);
                String nomPunt = element.get(POINT_ADDRESS);
                @Point.AccessType String accessType = element.get(ACCESS_KEY);
                UserPointSummary point = new UserPointSummary(idPunt, nomPunt,accessType);
                arrayList.add(point);




            }

        }

        return arrayList;
    }

    private Long parseLongKeyFromMap(String key, Map<String, Object> map) {
        long value = 0;
        if (map.containsKey(key)) {
            value = (Long) map.get(key);
        }
        return value;
    }

    private String parseStringKeyFromMap(String key, Map<String, Object> map) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        } else {
            return null;
        }
    }
}
