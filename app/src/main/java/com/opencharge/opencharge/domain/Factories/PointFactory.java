package com.opencharge.opencharge.domain.Factories;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.Entities.FirebasePoint;
import com.opencharge.opencharge.domain.Entities.Point;

import java.util.List;

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
                                String accesType, List<String> connectorTypeList, String schedule) {

        Point p = new Point();
        p.setLat(lat);
        p.setLon(lon);
        p.setTown(town);
        p.setStreet(street);
        p.setNumber(number);
        p.setAccessType(accesType);
        p.setConnectorTypeList(connectorTypeList);
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
        o.connectorTypeList = p.connectorTypeList;
        o.schedule = p.schedule;

        return o;
    }
}
