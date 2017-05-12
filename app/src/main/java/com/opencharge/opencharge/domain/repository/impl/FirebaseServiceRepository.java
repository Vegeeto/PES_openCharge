package com.opencharge.opencharge.domain.repository.impl;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.Entities.FirebaseService;
import com.opencharge.opencharge.domain.repository.ServiceRepository;

/**
 * Created by Oriol on 12/5/2017.
 */

public class FirebaseServiceRepository implements ServiceRepository {

    private FirebaseDatabase database;

    public FirebaseServiceRepository() {
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
    public void getServices(String point_id, GetServicesCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Services");

        //TODO: continue this
    }
}
