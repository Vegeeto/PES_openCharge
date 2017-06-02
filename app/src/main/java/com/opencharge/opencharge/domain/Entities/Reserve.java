package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * Created by Oriol on 9/5/2017.
 */

public class Reserve {

    private String id;
    private String serviceId;
    private String consumerUserId;
    private String supplierUserId;

    private Date day;
    private Date startHour;
    private Date endHour;

    private boolean ownerFinish;
    private boolean userFinish;
    private boolean canConfirm;

    public static final String CREATED = "Creada";
    public static final String ACCEPTED = "Acceptada";
    public static final String REJECTED = "Rebutjada";

    @StringDef({CREATED, ACCEPTED, REJECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    private @Reserve.State String state;

    public Reserve(Date day, Date startHour, Date endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.state = CREATED;
        canConfirm = false;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (this.id != null) {
            throw new InvalidParameterException("La reserva ja té un identificador assignat");
        }
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getConsumerUserId() {
        return consumerUserId;
    }

    public void setConsumerUserId(String consumerUserId) {
        this.consumerUserId = consumerUserId;
    }

    public String getSupplierUserId() {
        return supplierUserId;
    }

    public void setSupplierUserId(String supplierUserId) {
        this.supplierUserId = supplierUserId;
    }

    public @State String getState() {
        return state;
    }

    public void accept() {
        this.state = ACCEPTED;
    }

    public void reject() {
        this.state = REJECTED;
    }

    public boolean isMarkedAsFinishedByOwner() {
        return ownerFinish;
    }

    public boolean isMarkedAsFinishedByUser() {
        return userFinish;
    }

    public void markAsFinishedByOwner() {
        this.ownerFinish = true;
    }

    public void markAsFinishedByUser() {
        this.userFinish = true;
    }

    public boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

}
