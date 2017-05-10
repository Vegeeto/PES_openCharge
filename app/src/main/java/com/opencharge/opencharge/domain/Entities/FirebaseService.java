package com.opencharge.opencharge.domain.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/5/2017.
 */

public class FirebaseService {

    private String id;
    private String day;
    private String startHour;
    private String endHour;
    private Map<Integer, Integer> repeat;
    private String lastRepeat;

    //Empty constructor needed for Firebase
    public FirebaseService() {}

    public FirebaseService(String id) {
        this.id = id;
        repeat = new HashMap<>();
    }

    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public Map<Integer, Integer> getRepeat() {
        return repeat;
    }

    public void setRepeat(Map<Integer, Integer> repeat) {
        this.repeat = repeat;
    }

    public String getLastRepeat() {
        return lastRepeat;
    }

    public void setLastRepeat(String lastRepeat) {
        this.lastRepeat = lastRepeat;
    }

}
