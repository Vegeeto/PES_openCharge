package com.opencharge.opencharge.domain.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 9/5/2017.
 */

public class Service {

    private String id;
    private Date day;
    private Date startHour;
    private Date endHour;

    //Empty constructor needed for Firebase
    public Service() {}

    //Empty constructor needed for Firebase
    public Service(String id) {
        this.id = id;
    }

    public Service(Date day, Date startHour, Date endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }
}
