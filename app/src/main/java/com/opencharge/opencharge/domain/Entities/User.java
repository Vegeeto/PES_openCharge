package com.opencharge.opencharge.domain.Entities;

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
    private List<UserPointSummary> points;

    public User() {
        this.points = new ArrayList<>();
    }

    public User(String id) {
        this.id = id;
        this.points = new ArrayList<>();
    }

    public User (String username, String photo,  String email) {
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.points = new ArrayList<>();
    }

    public User (String username, String photo,  String email, List<UserPointSummary> points) {
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

    public void setPoints(List<UserPointSummary> punts) {
        this.points = punts;
    }

    public List<UserPointSummary> getPoints() {
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
