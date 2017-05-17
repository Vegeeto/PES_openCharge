package com.opencharge.opencharge.domain.Entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/5/2017.
 */

public class FirebaseReserve {

    private String id;
    private String day;
    private String startHour;
    private String endHour;

    //TODO: finish this class => ORIOL

    //Empty constructor needed for Firebase
    public FirebaseReserve() {}

    public FirebaseReserve(String id) {
        this.id = id;
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

}
