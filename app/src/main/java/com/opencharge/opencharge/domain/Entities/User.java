package com.opencharge.opencharge.domain.Entities;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by DmnT on 10/05/2017.
 */

public class User {

    private String email;
    private String username;
    private Integer minutes;
    private ArrayList<Pair<String,String>> punts;
    private ArrayList<Pair<String,String>> puntsReservats;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public void setPunts(ArrayList<Pair<String, String>> punts) {
        this.punts = punts;
    }

    public void setPuntsReservats(ArrayList<Pair<String, String>> puntsReservats) {
        this.puntsReservats = puntsReservats;
    }

    public void addPunt(Pair<String, String> punt){
        boolean trobat=false;
        for(int i=0;i<punts.size();i++) {
            if (punts.get(i).first.equals(punt.first)) {
                trobat = true;
            }
        }
        if(!trobat) {
            punts.add(punt);
        }
    }

    public void removePunt(String codipunt){
        boolean trobat=false;
        int index = 0;
        for(int i=0;i<punts.size();i++){
            if(punts.get(i).first.equals(codipunt)){
                trobat=true;
                index = i;
            }
        }
        if(trobat) {
            punts.remove(index);
        }
    }

    public void addPuntReservat(Pair<String, String> punt){
        boolean trobat=false;
        for(int i=0;i<puntsReservats.size();i++) {
            if (puntsReservats.get(i).first.equals(punt.first)) {
                trobat = true;
            }
        }
        if(!trobat) {
            puntsReservats.add(punt);
        }
    }

    public void removePuntReservat(String codipunt){
        boolean trobat=false;
        int index = 0;
        for(int i=0;i<puntsReservats.size();i++){
            if(puntsReservats.get(i).first.equals(codipunt)){
                trobat=true;
                index = i;
            }
        }
        if(trobat) {
            puntsReservats.remove(index);
        }
    }

    public String getEmail() {

        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public ArrayList<Pair<String, String>> getPunts() {
        return punts;
    }

    public ArrayList<Pair<String, String>> getPuntsReservats() {
        return puntsReservats;
    }
}
