package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.parsers.PointsParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebasePointsParser;
import com.opencharge.opencharge.domain.repository.PointsRepository;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

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
                Point[] points = parsePointsFromDataSnapshot(dataSnapshot);
                callback.onPointsRetrieved(points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void getPointById(String pointId, final GetPointByIdCallback callback) {
        DatabaseReference myRef = database.getReference("Points").child(pointId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Point point = parsePointFromSnapshot(dataSnapshot);
                callback.onPointRetrieved(point);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void addReserveToPoint(Reserve reserve, final AddReserveToPointCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(reserve.getPointId()).child("Reserves");

        String dayPath = serializeReserveDate(reserve);
        myRef = myRef.child(dayPath);

        myRef.push().setValue(reserve.getId(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.onReserveAddedToPoint();
            }
        });

    }

    @Override
    public void deletePoint(String pointId, final DeletePointCallback callback) {

        Log.e("DeletePoint", "Hola");
        DatabaseReference myRef = database.getReference("Points").child(pointId);

        myRef.push().removeValue();

        if (database.getReference("Points").child(pointId) == null) {
            callback.onPointDeleted();
            Log.e("DeletePoint", "Eliminat");
        } else {
            callback.onError();
            Log.e("DeletePoint", "Error");
        }
    }

    private String serializeReserveDate(Reserve reserve) {
        DateConversion dateConversion = new DateConversionImpl();
        return dateConversion.ConvertDateToPath(reserve.getDay());
    }

    private Point[] parsePointsFromDataSnapshot(DataSnapshot dataSnapshot) {
        Point[] points = new Point[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Point point = parsePointFromSnapshot(snapshot);
            if (point != null) {
                points[index] = point;
                ++index;
            }
        }

        return points;
    }

    private Point parsePointFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return pointsParser.parseFromMap(key, map);
        }
        return null;
    }

    public void createPoint(Point point, final CreatePointCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        Map<String, Object> serializedPoint = pointsParser.serializePoint(point);
        myRef.push().setValue(serializedPoint, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                String postId = dr.getKey();
                callback.onPointCreated(postId);
            }

            ;
        });
    }
}
