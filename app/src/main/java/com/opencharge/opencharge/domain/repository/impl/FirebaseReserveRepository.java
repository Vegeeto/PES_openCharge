package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.FirebaseReserve;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseReserveParser;
import com.opencharge.opencharge.domain.repository.ReserveRepository;

import java.util.Map;

/**
 * Created by Oriol on 12/5/2017.
 */

public class FirebaseReserveRepository implements ReserveRepository {

    private ReserveParser serviceParser;
    private FirebaseDatabase database;

    //TODO: finish this class => ORIOL

    public FirebaseReserveRepository() {
        this.serviceParser = new FirebaseReserveParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void createReserve(String point_id, final FirebaseReserve service, final CreateReserveCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Reserves");
        myRef.push().setValue(service, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("Record saved!");
                String serviceId = dr.getKey();
                callback.onReserveCreated(serviceId);
            }

            ;
        });
    }

    @Override
    public void getReserves(String point_id, final GetReservesCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Reserves");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reserve[] services = parseReservesFromDataSnapshot(dataSnapshot);
                callback.onReservesRetrieved(services);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });

    }

    private Reserve[] parseReservesFromDataSnapshot(DataSnapshot dataSnapshot) {
        Reserve[] services = new Reserve[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Reserve service = parseReserveFromSnapshot(snapshot);
            if (service != null) {
                services[index] = service;
                ++index;
            }
        }

        return services;
    }

    private Reserve parseReserveFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return serviceParser.parseFromMap(key, map);
        }
        return null;
    }

}