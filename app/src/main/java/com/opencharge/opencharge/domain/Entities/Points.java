package com.opencharge.opencharge.domain.Entities;

/**
 * Created by Crjs on 24/03/2017.
 */

public class Points {
    //xCoord and yCoord string
    public float lat;
    public float lon;

    public Points() {}

    //Getters and setters
    public float getLatCoord() {
        return lat;
    }

    public float getLonCoord() {
        return lon;
    }

    @Override
    public String toString() {
        return "Points{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
