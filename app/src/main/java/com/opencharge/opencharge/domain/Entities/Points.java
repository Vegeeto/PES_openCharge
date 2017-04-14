package com.opencharge.opencharge.domain.Entities;

/**
 * Created by Crjs on 24/03/2017.
 */

public class Points {
    public enum Acces {
        PUBLIC,
        PRIVAT,
        PARTICULAR;
    }

    public int id;

    //xCoord and yCoord string
    public float lat;
    public float lon;

    public String poblacio;
    public String carrer;
    public String numero;

    public Acces acces;
    public String conector;
    public String horari;

    public Points() {}

    //Getters and setters
    public float getLatCoord() {
        return lat;
    }

    public float getLonCoord() {
        return lon;
    }

    public int getId() {
        return id;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public String getCarrer() {
        return carrer;
    }

    public String getNumero() {
        return numero;
    }

    public Acces getAcces() {
        return acces;
    }

    public String getConector() {
        return conector;
    }

    public String getHorari() {
        return horari;
    }

    @Override
    public String toString() {
        return "Points{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }

}
