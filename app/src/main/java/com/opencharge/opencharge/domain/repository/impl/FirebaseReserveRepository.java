package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.parsers.ReserveParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseReserveParser;
import com.opencharge.opencharge.domain.repository.ReserveRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private long childsToWaitByPointAndDay;

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
    public void getReservesForPointAtDay(String point_id, Date day, final GetReservesForPointAtDayCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Reserves");

        String dayPath = serializeReserveDate(day);
        myRef = myRef.child(dayPath);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Reserve> reserves = new ArrayList<Reserve>();
                if (dataSnapshot.getChildrenCount() == 0) {
                    finishRetrieveReservesByPointAndDay(reserves, callback);
                }
                else {
                    childsToWaitByPointAndDay = dataSnapshot.getChildrenCount();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String reserveId = (String)snapshot.getValue();
                        getReserveById(reserveId, new FirebaseReserveRepository.GetReserveByIdCallback(){
                            @Override
                            public void onReserveRetrieved(Reserve reserve) {
                                reserves.add(reserve);

                                if (--childsToWaitByPointAndDay < 1) {
                                    finishRetrieveReservesByPointAndDay(reserves, callback);
                                }
                            }

                            @Override
                            public void onError() {
                                callback.onError();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });

    }

    private void finishRetrieveReservesByPointAndDay(List<Reserve> reservesList, GetReservesForPointAtDayCallback callback) {
        Reserve[] reserves = new Reserve[reservesList.size()];
        reserves = reservesList.toArray(reserves);
        callback.onReservesRetrieved(reserves);
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
                if (dataSnapshot.getChildrenCount() == 0) {
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

    private String serializeReserveDate(Date date) {
        DateConversion dateConversion = new DateConversionImpl();
        return dateConversion.ConvertDateToPath(date);
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