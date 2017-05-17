package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.FirebaseService;
import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.parsers.ServiceParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseServicePaser;
import com.opencharge.opencharge.domain.repository.ServiceRepository;

import java.util.Map;

/**
 * Created by Oriol on 12/5/2017.
 */

public class FirebaseServiceRepository implements ServiceRepository {

    private ServiceParser serviceParser;
    private FirebaseDatabase database;

    public FirebaseServiceRepository() {
        this.serviceParser = new FirebaseServicePaser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void createService(String point_id, final FirebaseService service, final CreateServiceCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");
        myRef.push().setValue(service, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("Record saved!");
                String serviceId = dr.getKey();
                callback.onServiceCreated(serviceId);
            }

            ;
        });
    }

    @Override
    public void getServices(String point_id, final GetServicesCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Service[] services = parseServicesFromDataSnapshot(dataSnapshot);
                callback.onServicesRetrieved(services);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });

    }

    private Service[] parseServicesFromDataSnapshot(DataSnapshot dataSnapshot) {
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

    private Service parseServiceFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return serviceParser.parseFromMap(key, map);
        }
        return null;
    }

}