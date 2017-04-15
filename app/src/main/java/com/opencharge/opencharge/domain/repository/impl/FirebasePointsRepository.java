package com.opencharge.opencharge.domain.repository.impl;

import android.graphics.Point;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Points;
import com.opencharge.opencharge.domain.repository.PointsRepository;

/**
 * Created by ferran on 15/3/17.
 */

public class FirebasePointsRepository implements PointsRepository {
    @Override
    public void getPoints(final GetPointsCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Points");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Points[] pArray = new Points[(int)dataSnapshot.getChildrenCount()];
                int index = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Points p = snapshot.getValue(Points.class);
                    p.id = snapshot.getKey();
                    Log.d("FirebaseRepo", p.toString());
                    pArray[index] = p;
                    ++index;
                }
                callback.onPointsRetrieved(pArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }
}
