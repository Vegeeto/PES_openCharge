package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.android.gms.games.snapshot.Snapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.FirebaseReserve;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.parsers.ReserveParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseReserveParser;
import com.opencharge.opencharge.domain.repository.ReserveRepository;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Oriol on 12/5/2017.
 */

public class FirebaseReserveRepository implements ReserveRepository {

    private ReserveParser reserveParser;
    private FirebaseDatabase database;
    // We need a global var to count the childs to wait
    private long childsToWait;

    //TODO: finish this class => ORIOL

    public FirebaseReserveRepository() {
        this.reserveParser = new FirebaseReserveParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void createReserve(String point_id, final FirebaseReserve reserve, final CreateReserveCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Reserves");
        myRef.push().setValue(reserve, new DatabaseReference.CompletionListener() {

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

    @Override
    public void getReservesAsConsumerByUserId(String userId, final GetReservesByUserIdCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        myRef = myRef.child(userId);
        myRef = myRef.child("ReservesConsumer");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {;
                final ArrayList<Reserve> reserves = new ArrayList<Reserve>();
                childsToWait = dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String reserveId = (String)snapshot.getValue();
                    getReserveById(reserveId, new FirebaseReserveRepository.GetReserveByIdCallback(){
                        @Override
                        public void onReserveRetrieved(Reserve reserve) {
                            reserves.add(reserve);
                            if(--childsToWait < 1){
                                callback.onReservesRetrieved(reserves);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void getReservesAsSupplierByUserId(String userId, final GetReservesByUserIdCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        myRef = myRef.child(userId);
        myRef = myRef.child("ReservesSupplier");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {;
                final ArrayList<Reserve> reserves = new ArrayList<Reserve>();
                childsToWait = dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String reserveId = (String)snapshot.getValue();
                    getReserveById(reserveId, new FirebaseReserveRepository.GetReserveByIdCallback(){
                        @Override
                        public void onReserveRetrieved(Reserve reserve) {
                            reserves.add(reserve);
                            if(--childsToWait < 1){
                                callback.onReservesRetrieved(reserves);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void getReserveById(String reserveId, final GetReserveByIdCallback callback) {
        DatabaseReference myRef = database.getReference("Reserves");
        myRef = myRef.child(reserveId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reserve res = parseReserveFromSnapshot(dataSnapshot);
                callback.onReserveRetrieved(res);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private Reserve[] parseReservesFromDataSnapshot(DataSnapshot dataSnapshot) {
        Reserve[] reserves = new Reserve[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Reserve reserve = parseReserveFromSnapshot(snapshot);
            if (reserve != null) {
                reserves[index] = reserve;
                ++index;
            }
        }

        return reserves;
    }
    private Reserve parseReserveFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return reserveParser.parseFromMap(key, map);
        }
        return null;
    }

}