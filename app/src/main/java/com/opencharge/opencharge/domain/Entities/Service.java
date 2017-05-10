package com.opencharge.opencharge.domain.Entities;

import java.sql.Time;
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
    private Map<Integer, Integer> repeat;
    private Date lastRepeat;

    //Empty constructor needed for Firebase
    public Service() {}

    public Service(Date day, Time startHour, Time endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        repeat = new HashMap<>();
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

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public Map<Integer, Integer> getRepeat() {
        return repeat;
    }

    public void setRepeatMonday() {
        repeat.put(0, 1);
    }

    public void setRepeatTuesday() {
        repeat.put(1, 1);
    }

    public void setRepeatWednesday() {
        repeat.put(2, 1);
    }

    public void setRepeatThursday() {
        repeat.put(3, 1);
    }

    public void setRepeatFriday() {
        repeat.put(4, 1);
    }

    public void setRepeatSaturday() {
        repeat.put(5, 1);
    }

    public void setRepeatSunday() {
        repeat.put(6, 1);
    }

    public Date getLastRepeat() {
        return lastRepeat;
    }

    public void setLastRepeat(Date lastRepeat) {
        this.lastRepeat = lastRepeat;
    }
}
