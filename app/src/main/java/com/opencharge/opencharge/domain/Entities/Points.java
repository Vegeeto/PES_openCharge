package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Crjs on 24/03/2017.
 */

public class Points {
    public static final String PUBLIC_ACCESS = "public";
    public static final String PRIVATE_ACCESS = "private";
    public static final String INDIVIDUAL_ACCESS = "individual";

    @StringDef({PUBLIC_ACCESS, PRIVATE_ACCESS, INDIVIDUAL_ACCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessType {}

    public static final String UNKNOWN_CONNECTOR = "unknown";
    public static final String SLOW_CONNECTOR = "slow";
    public static final String FAST_CONNECTOR = "fast";
    public static final String RAPID_CONNECTOR = "rapid";

    @StringDef({UNKNOWN_CONNECTOR, SLOW_CONNECTOR, FAST_CONNECTOR, RAPID_CONNECTOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectorType {}

    public String id;

    //xCoord and yCoord string
    public float lat;
    public float lon;

    public String town;
    public String street;
    public String number;

    public @Points.AccessType String accessType;
    public @Points.ConnectorType String connectorType;
    public String schedule;

    //Empty constructor needed for Firebase
    public Points() {}

    //Getters and setters
    public float getLatCoord() {
        return lat;
    }

    public float getLonCoord() {
        return lon;
    }

    public String getId() {
        return id;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public @AccessType String getAccessType() {
        return accessType;
    }

    public @ConnectorType String getConnectorType() {
        return connectorType;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAccessType(@AccessType String accessType) {
        this.accessType = accessType;
    }

    public void setConnectorType(@ConnectorType String connectorType) {
        this.connectorType = connectorType;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Points{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", accessType=" + accessType +
                ", connectorType='" + connectorType + '\'' +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}
