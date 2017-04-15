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
import com.opencharge.opencharge.domain.parsers.PointsParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebasePointsParser;
import com.opencharge.opencharge.domain.repository.PointsRepository;

import java.util.Map;

/**
 * Created by ferran on 15/3/17.
 */

public class FirebasePointsRepository implements PointsRepository {

    private PointsParser pointsParser;
    private FirebaseDatabase database;

    public FirebasePointsRepository() {
        this.pointsParser = new FirebasePointsParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void getPoints(final GetPointsCallback callback) {
        DatabaseReference myRef = database.getReference("Points");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Points[] points = parsePointsFromDataSnapshot(dataSnapshot);
                callback.onPointsRetrieved(points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    private Points[] parsePointsFromDataSnapshot(DataSnapshot dataSnapshot) {
        Points[] points = new Points[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Points point = parsePointFromSnapshot(snapshot);
            if (point != null) {
                points[index] = point;
                ++index;
            }
        }

        return points;
    }

    private Points parsePointFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return pointsParser.parseFromMap(key, map);
        }
        return null;
    }
}
