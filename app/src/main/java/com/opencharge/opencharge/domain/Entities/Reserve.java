package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 9/5/2017.
 */

public class Reserve {

    private String id;
    private Date day;
    private Date startHour;
    private Date endHour;
    private String userid;

    public static final String UNKNOWN = "Desconegut";
    public static final String ACCEPTED = "Rebutjada";
    public static final String REJECTED = "Acceptada";

    @StringDef({UNKNOWN, ACCEPTED, REJECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    private @Reserve.State String state;


    //Empty constructor needed for Firebase
    public Reserve() {}

    //Empty constructor needed for Firebase
    public Reserve(String id) {
        this.id = id;
    }

    public Reserve(Date day, Date startHour, Date endHour) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public @State String getState() {
        return state;
    }

    public void setState(@State String state) {
        this.state = state;
    }



}
