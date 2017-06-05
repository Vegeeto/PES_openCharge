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
    private String pointId;
    private String consumerUserId;
    private String supplierUserId;

    private Date day;
    private Date startHour;
    private Date endHour;

    private boolean consumerFinish;
    private boolean supplierFinish;
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
        consumerFinish = false;
        supplierFinish = false;
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

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
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

    public boolean isMarkedAsFinishedByConsumer() {
        return consumerFinish;
    }

    public boolean isMarkedAsFinishedBySupplier() {
        return supplierFinish;
    }

    public void markAsFinishedByConsumer() {
        this.consumerFinish = true;
    }

    public void markAsFinishedBySupplier() {
        this.supplierFinish = true;
    }

    public boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

}
