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
    private List<Pair<String,String>> puntsCreats;

    public User() {

    }

    public User(String id) {
        this.id = id;
    }

    public User (String username, String photo,  String email, ArrayList<Pair<String,String>> puntsCreats) {
        this.username = username;
        this.photo = photo;
        this.email = email;
        this.puntsCreats = puntsCreats;
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

    public void setPunts(List<Pair<String, String>> punts) {
        this.puntsCreats = punts;
    }

    public void addPunt(Pair<String, String> punt){
        boolean trobat = false;
        for (int i = 0; i < puntsCreats.size(); i++) {
            if (puntsCreats.get(i).first.equals(punt.first)) {
                trobat = true;
            }
        }
        if (!trobat) {
            puntsCreats.add(punt);
        }
    }

    public void removePunt(String codipunt){
        boolean trobat = false;
        int index = 0;
        for (int i = 0; i < puntsCreats.size(); i++){
            if (puntsCreats.get(i).first.equals(codipunt)){
                trobat=true;
                index = i;
            }
        }
        if (trobat) {
            puntsCreats.remove(index);
        }
    }

    public List<Pair<String, String>> getPunts() {
        return puntsCreats;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", minutes=" + minutes +
                ", puntsCreats=" + puntsCreats +
                ", id='" + id + '\'' +
                '}';
    }
}
