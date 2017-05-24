package com.opencharge.opencharge.domain.Entities;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Usuario on 23/05/2017.
 */

public class FirebaseUser {

    private String name;
    private String photo;
    private String password;
    private String email;
    private String username;
    private String profilepic;
    private Integer minutes;
    private ArrayList<Pair<String,String>> puntsCreats;
    private ArrayList<Pair<String,String>> puntsReservats;

    public String id;

    public FirebaseUser() {

    }

    public FirebaseUser(String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public String getPhoto () {
        return photo;
    }

    public String getPassword () {
        return password;
    }

    public String getEmail () {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public String getProfilepic() { return profilepic; }

    public ArrayList<Pair<String, String>> getPunts() {
        return puntsCreats;
    }

    public ArrayList<Pair<String, String>> getPuntsReservats() {
        return puntsReservats;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setProfilepic(String profilepic) { this.profilepic = profilepic; }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public void setPunts(ArrayList<Pair<String, String>> punts) {
        this.puntsCreats = punts;
    }

    public void setPuntsReservats(ArrayList<Pair<String, String>> puntsReservats) {
        this.puntsReservats = puntsReservats;
    }


}
