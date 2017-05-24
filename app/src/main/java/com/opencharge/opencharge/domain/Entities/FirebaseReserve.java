package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/5/2017.
 */

public class FirebaseReserve {

    private String id;
    private String serviceId;
    private String day;
    private String startHour;
    private String endHour;
    private String userId;
    private boolean ownerFinish;
    private boolean userFinish;

    private @Reserve.State String state;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public @Reserve.State
    String getState() {
        return state;
    }

    public void setState(@Reserve.State String state) {
        this.state = state;
    }

    public boolean isOwnerFinish() {
        return ownerFinish;
    }

    public void setOwnerFinish(boolean ownerFinish) {
        this.ownerFinish = ownerFinish;
    }

    public boolean isUserFinish() {
        return userFinish;
    }

    public void setUserFinish(boolean userFinish) {
        this.userFinish = userFinish;
    }

}
