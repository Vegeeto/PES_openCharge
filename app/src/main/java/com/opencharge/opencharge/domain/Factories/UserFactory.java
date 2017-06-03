package com.opencharge.opencharge.domain.Factories;

import android.support.v4.util.Pair;

import com.opencharge.opencharge.domain.Entities.User;

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

    public User createNewUser(String name, String photo, String email, List<Pair<String,String>> puntsCreats) {

        User u = new User();
        u.setUsername(name);
        u.setPhoto(photo);
        u.setEmail(email);
        u.setMinutes(0);
        u.setPoints(puntsCreats);
        return u;
    }

}
