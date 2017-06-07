package com.opencharge.opencharge.domain.Factories;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;

import java.util.List;

/**
 * Created by Usuario on 23/05/2017.
 */

public class UserFactory {

    private static UserFactory instance;

    private UserFactory() {}

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User createNewUser(String name, String photo, String email, List<UserPointSummary> points) {
        User u = new User();
        u.setUsername(name);
        u.setPhoto(photo);
        u.setEmail(email);
        u.setMinutes(1000);
        u.setPoints(points);
        return u;
    }

}
