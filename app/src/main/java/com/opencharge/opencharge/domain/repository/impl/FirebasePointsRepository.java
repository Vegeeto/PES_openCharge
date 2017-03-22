package com.opencharge.opencharge.domain.repository.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.repository.PointsRepository;

/**
 * Created by ferran on 15/3/17.
 */

public class FirebasePointsRepository implements PointsRepository {
    @Override
    public void getPoints(final GetPointsCallback callback) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Punts");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println(snapshot.getValue());


                }

                callback.onPointsRetrieved("aqui el punts");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });
    }
}
