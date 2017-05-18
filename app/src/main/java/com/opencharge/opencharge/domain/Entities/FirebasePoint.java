package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Crjs on 26/04/2017.
 */

public class FirebasePoint {
    public static final String UNKNOWN_ACCESS = "unknown";
    public static final String PUBLIC_ACCESS = "public";
    public static final String PRIVATE_ACCESS = "private";
    public static final String INDIVIDUAL_ACCESS = "individual";

    @StringDef({UNKNOWN_ACCESS, PUBLIC_ACCESS, PRIVATE_ACCESS, INDIVIDUAL_ACCESS})
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
    public double lat;
    public double lon;

    public String town;
    public String street;
    public String number;

    public @Point.AccessType String accessType;
    public @Point.ConnectorType String connectorType;

    public String schedule;

    //Empty constructor needed for Firebase
    public FirebasePoint() {}

    public FirebasePoint(String id) {
        this.id = id;
    }

    //Getters and setters
    public double getLat() {
        return lat;
    }

    public double getLon() {
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

    public @Point.AccessType
    String getAccessType() {
        return accessType;
    }

    public @Point.ConnectorType
    String getConnectorType() {
        return connectorType;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
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

    public void setAccessType(@Point.AccessType String accessType) {
        this.accessType = accessType;
    }

    public void setConnectorType(@Point.ConnectorType String connectorType) {
        this.connectorType = connectorType;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

}
