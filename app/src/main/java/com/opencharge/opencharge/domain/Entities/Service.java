package com.opencharge.opencharge.domain.Entities;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Oriol on 9/5/2017.
 */

public class Service {

    private String id;
    private Date day;
    private Time startHour;
    private Time endHour;
    private List<String> repeat;
    private Date lastRepeat;

    public Service(Date day, Time startHour) {
        this.day = day;
        this.startHour = startHour;
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

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public List<String> getRepeat() {
        return repeat;
    }

    public void setRepeat(List<String> repeat) {
        this.repeat = repeat;
    }

    public Date getLastRepeat() {
        return lastRepeat;
    }

    public void setLastRepeat(Date lastRepeat) {
        this.lastRepeat = lastRepeat;
    }
}
