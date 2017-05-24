package com.opencharge.opencharge.domain.Entities;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by DmnT on 10/05/2017.
 */

public class User {
    private String name;
    private String photo;
    private String password;
    private String email;
    private String username;
    private String profilepic;
    private Integer minutes;
    private ArrayList<Pair<String,String>> puntsCreats;
    private ArrayList<Pair<String,String>> puntsReservats;

    public User() {

    }

    public User (String name, String photo, String password, String email, ArrayList<Pair<String,String>> puntsCreats, ArrayList<Pair<String,String>> puntsReservats) {
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.email = email;
        this.puntsCreats = puntsCreats;
        this.puntsReservats = puntsReservats;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getProfilepic() { return profilepic; }

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

    public void addPuntReservat(Pair<String, String> punt){
        boolean trobat = false;
        for (int i = 0; i < puntsReservats.size(); i++) {
            if (puntsReservats.get(i).first.equals(punt.first)) {
                trobat = true;
            }
        }
        if (!trobat) {
            puntsReservats.add(punt);
        }
    }

    public void removePuntReservat(String codipunt){
        boolean trobat = false;
        int index = 0;
        for (int i = 0; i < puntsReservats.size(); i++){
            if (puntsReservats.get(i).first.equals(codipunt)){
                trobat = true;
                index = i;
            }
        }
        if (trobat) {
            puntsReservats.remove(index);
        }
    }

    public ArrayList<Pair<String, String>> getPunts() {
        return puntsCreats;
    }

    public ArrayList<Pair<String, String>> getPuntsReservats() {
        return puntsReservats;
    }
}
