package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private long childsToWaitSupplier;
    private long childsToWaitConsumer;

    public FirebaseReserveRepository() {
        this.reserveParser = new FirebaseReserveParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void createReserve(Reserve reserve, final CreateReserveCallback callback) {
        DatabaseReference myRef = database.getReference("Reserves");
        Map<String, Object> serializedReserve = reserveParser.serializeReserve(reserve);
        myRef.push().setValue(serializedReserve, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                String reserveId = dr.getKey();
                callback.onReserveCreated(reserveId);
            }
        });
    }

    @Override
    public void getReserves(String point_id, final GetReservesCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Reserves");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Reserve> reserves = new ArrayList<Reserve>();
                if(dataSnapshot.getChildrenCount() == 0) {
                    callback.onReservesRetrieved(reserves);
                }
                childsToWaitConsumer = dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String reserveId = (String)snapshot.getValue();
                    getReserveById(reserveId, new FirebaseReserveRepository.GetReserveByIdCallback(){
                        @Override
                        public void onReserveRetrieved(Reserve reserve) {
                            if((reserve.getState() != Reserve.REJECTED) && (reserve.getState() != Reserve.ACCEPTED))reserves.add(reserve);
                            if(--childsToWaitConsumer < 1){
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Reserve> reserves = new ArrayList<Reserve>();
                if(dataSnapshot.getChildrenCount() == 0) {
                    callback.onReservesRetrieved(reserves);
                }
                childsToWaitSupplier = dataSnapshot.getChildrenCount();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String reserveId = (String)snapshot.getValue();
                    getReserveById(reserveId, new FirebaseReserveRepository.GetReserveByIdCallback(){
                        @Override
                        public void onReserveRetrieved(Reserve reserve) {
                            if((reserve.getState() != Reserve.REJECTED) && (reserve.getState() != Reserve.ACCEPTED))reserves.add(reserve);
                            if(--childsToWaitSupplier < 1){
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

    @Override
    public void updateConfirmationsReserve(Reserve r) {
        DatabaseReference myRef = database.getReference("Reserves");
        myRef = myRef.child(r.getId());
        myRef = myRef.child("markedAsFinishedByOwner");
        myRef.setValue(r.isMarkedAsFinishedByConsumer());

        myRef = database.getReference("Reserves");
        myRef = myRef.child(r.getId());
        myRef = myRef.child("markedAsFinishedByUser");
        myRef.setValue(r.isMarkedAsFinishedBySupplier());

        updateStateReserve(r);
    }

    @Override
    public void updateStateReserve(Reserve r) {
        DatabaseReference myRef = database.getReference("Reserves");
        myRef = myRef.child(r.getId());
        myRef = myRef.child("state");
        myRef.setValue(r.getState());
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