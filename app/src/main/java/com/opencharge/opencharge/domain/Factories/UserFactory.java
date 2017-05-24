package com.opencharge.opencharge.domain.Factories;

import android.util.Pair;

import com.opencharge.opencharge.domain.Entities.FirebaseUser;
import com.opencharge.opencharge.domain.Entities.User;

import java.util.ArrayList;

/**
 * Created by Usuario on 23/05/2017.
 */

public class UserFactory {

    private static UserFactory instance;

    private UserFactory() {}

    public static UserFactory getInstance() {
        if(instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User createNewUser(String name, String photo, String email, ArrayList<Pair<String,String>> puntsCreats, ArrayList<Pair<String,String>> puntsReservats) {

        User u = new User();
        u.setUsername(name);
        u.setPhoto(photo);
        u.setEmail(email);
        u.setMinutes(0);
        u.setPunts(puntsCreats);
        u.setPuntsReservats(puntsReservats);
        return u;
    }
    public void setUserId(User u, String id){
        u.id = id;
    }

    public FirebaseUser pointToFirebasePoint(User p){

        FirebaseUser u = new FirebaseUser();
        u.setUsername(p.getUsername());
        u.setPhoto(p.getPhoto());
        u.setEmail(p.getEmail());
        u.setPunts(p.getPunts());
        u.setPuntsReservats(p.getPuntsReservats());
        u.setMinutes(p.getMinutes());
        return u;
    }
}
