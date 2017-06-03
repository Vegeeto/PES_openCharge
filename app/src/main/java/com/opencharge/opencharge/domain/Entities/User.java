package com.opencharge.opencharge.domain.Entities;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DmnT on 10/05/2017.
 */

public class User {
    private String id;
    private String photo;
    private String email;
    private String username;
    private Integer minutes;

    //arraylist de pairs, on el primer component del pair és el codi del punt, i el segon és la direcció de carrer(que es mostrarà al perfil)
    private List<Pair<String,String>> points;

    public User() {

    }

    public User(String id) {
        this.id = id;
    }

    public User (String username, String photo,  String email, ArrayList<Pair<String,String>> points) {
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getPhoto () {
        return photo;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public void setPoints(List<Pair<String, String>> punts) {
        this.points = punts;
    }

    public List<Pair<String, String>> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", minutes=" + minutes +
                ", points=" + points +
                ", id='" + id + '\'' +
                '}';
    }
}
