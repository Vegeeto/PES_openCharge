package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import com.opencharge.opencharge.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Crjs on 24/03/2017.
 */

public class Point {
    public static final String UNKNOWN_ACCESS = "Desconegut";
    public static final String PUBLIC_ACCESS = "Public";
    public static final String PRIVATE_ACCESS = "Privat";
    public static final String PARTICULAR_ACCESS = "Particular";

    @StringDef({UNKNOWN_ACCESS, PUBLIC_ACCESS, PRIVATE_ACCESS, PARTICULAR_ACCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessType {}

    public static final String UNKNOWN_CONNECTOR = "Desconegut";
    public static final String SLOW_CONNECTOR = "Lent";
    public static final String FAST_CONNECTOR = "Ràpid";
    public static final String RAPID_CONNECTOR = "Molt ràpid";

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
    public Point() {}

    public Point(String id) {
        this.id = id;
    }

    //Getters and setters
    public double getLatCoord() {
        return lat;
    }

    public double getLonCoord() {
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
        return "Point{" +
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

    public String getAddress() {
        return street + " " + number + ", " + town;
    }


    public static final int getDrawableForAccess(String access) {
        switch(access) {
            case Point.PUBLIC_ACCESS:
                return R.drawable.ic_point_public;
            case Point.PRIVATE_ACCESS:
                return R.drawable.ic_point_private;
            case Point.PARTICULAR_ACCESS:
                return R.drawable.ic_point_particular;
            default:
                return R.drawable.ic_point_unknown;
        }
    }

    public static final int getDrawableForConnector(String connectorType) {
        switch(connectorType) {
            case Point.SLOW_CONNECTOR:
                return R.drawable.ic_point_public;
            case Point.FAST_CONNECTOR:
                return R.drawable.ic_point_private;
            case Point.RAPID_CONNECTOR:
                return R.drawable.ic_point_particular;
            default:
                return R.drawable.ic_point_unknown;
        }
    }
}
