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

    public int id;

    //xCoord and yCoord string
    public float lat;
    public float lon;

    public String poblacio;
    public String carrer;
    public String numero;

    public @Points.AccessType String acces;
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

    public @AccessType String getAcces() {
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
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", poblacio='" + poblacio + '\'' +
                ", carrer='" + carrer + '\'' +
                ", numero='" + numero + '\'' +
                ", acces=" + acces +
                ", conector='" + conector + '\'' +
                ", horari='" + horari + '\'' +
                '}';
    }
}
