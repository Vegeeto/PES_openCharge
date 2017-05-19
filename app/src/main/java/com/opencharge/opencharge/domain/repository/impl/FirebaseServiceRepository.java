package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.parsers.ServiceParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseServiceParser;
import com.opencharge.opencharge.domain.repository.ServiceRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Oriol on 12/5/2017.
 */

public class FirebaseServiceRepository implements ServiceRepository {

    private ServiceParser serviceParser;
    private FirebaseDatabase database;

    public FirebaseServiceRepository() {
        this.serviceParser = new FirebaseServiceParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void createService(String point_id, Service service, final CreateServiceCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");
        Map<String, Object> serializedService = serviceParser.serializeService(service);
        myRef.push().setValue(serializedService, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                callback.onServiceCreated();
            }
        });
    }

    @Override
    public void createServices(String point_id, Service[] services, final CreateServicesCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");

        for (int i = 0 ; i < services.length ; i++) {
            Map<String, Object> serializedService = serviceParser.serializeService(services[i]);

            boolean isLast = (i == services.length - 1);
            if (!isLast) {
                myRef.push().setValue(serializedService);
            }
            else {
                myRef.push().setValue(serializedService, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError de, DatabaseReference dr) {
                        callback.onServicesCreated();
                    }
                });
            }
        }
    }

    @Override
    public void getServicesForPointAtDay(String point_id, Date day, final GetServicesForPointAtDayCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Service[] services = parseServicesFromDataSnapshot(dataSnapshot);
                    callback.onServicesRetrieved(services);
                } catch (ParseException e) {
                    Log.e("SERVICES REPO", "Error on parse");
                    callback.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("SERVICES REPO","ERROR: "+databaseError.toString());
            }
        });

    }

    private Service[] parseServicesFromDataSnapshot(DataSnapshot dataSnapshot) throws ParseException {
        Service[] services = new Service[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Service service = parseServiceFromSnapshot(snapshot);
            if (service != null) {
                services[index] = service;
                ++index;
            }
        }

        return services;
    }

    private Service parseServiceFromSnapshot(DataSnapshot snapshot) throws ParseException {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return serviceParser.parseFromMap(key, map);
        }
        return null;
    }

}