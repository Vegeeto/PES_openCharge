package com.opencharge.opencharge.domain.Factories;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.Entities.FirebasePoint;
import com.opencharge.opencharge.domain.Entities.Point;

/**
 * Created by Crjs on 19/04/2017.
 */

public class PointFactory {

    private static PointFactory instance;

    private PointFactory() {}

    public static PointFactory getInstance() {
        if(instance == null) {
            instance = new PointFactory();
        }
        return instance;
    }

    public Point createNewPoint(double lat, double lon, String town, String street, String number,
                                String accesType, String connectorType, String schedule) {

        Point p = new Point();
        p.setLat(lat);
        p.setLon(lon);
        p.setTown(town);
        p.setStreet(street);
        p.setNumber(number);
        p.setAccessType(accesType);
        p.setConnectorType(connectorType);
        p.setSchedule(schedule);
        return p;
    }
    public void setPointId(Point p, String id){
        p.id = id;
    }

    public FirebasePoint pointToFirebasePoint(Point p){

        FirebasePoint o = new FirebasePoint();
        o.lat = p.lat;
        o.lon = p.lon;
        o.town = p.town;
        o.street = p.street;
        o.number = p.number;
        o.accessType = p.accessType;
        o.connectorType = p.connectorType;
        o.schedule = p.schedule;

        return o;
    }
}
