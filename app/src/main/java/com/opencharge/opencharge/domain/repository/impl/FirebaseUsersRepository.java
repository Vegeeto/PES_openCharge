package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.parsers.UsersParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseUsersParser;
import com.opencharge.opencharge.domain.repository.UsersRepository;

import java.util.Map;

/**
 * Created by DmnT on 18/05/2017.
 */

public class FirebaseUsersRepository implements UsersRepository {

    private UsersParser usersParser;
    private FirebaseDatabase database;

    public FirebaseUsersRepository() {
        this.usersParser = new FirebaseUsersParser();
        this.database = FirebaseDatabase.getInstance();
    }

    @Override
    public void getUsers(final GetUsersCallback callback) {
        DatabaseReference myRef = database.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User[] users = parsePointsFromDataSnapshot(dataSnapshot);
                callback.onUsersRetrieved(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void getUserById(String userId, final GetUserByIdCallback callback) {
        DatabaseReference myRef = database.getReference("Users").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = parseUserFromSnapshot(dataSnapshot);
                callback.onUserRetrieved(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    @Override
    public void createUser(User user, final CreateUserCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        myRef.push().setValue(user, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                Log.d("CrearUser","Record saved!");
                String postId = dr.getKey();
                callback.onUserCreated(postId);
            }

            ;
        });
    }

    @Override
    public void addSupplyReserveToUser(final String reserveId, String userId, final AddReserveToUser callback) {
        DatabaseReference myRef = database.getReference("Users");
        myRef = myRef.child(userId).child("ReservesSupplier");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: implement add new id to list
//                List<String> savedIds = (List<>)dataSnapshot.getValue();
//                if( savedIds === null ) {
//                    savedIds = new ArrayList<>();
//                }
//                savedIds.add(reserveId);
//
//                myRef.updateChildren(savedIds);
                callback.onReserveAdded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    @Override
    public void addConsumerReserveToUser(String reserveId, String userId, AddReserveToUser callback) {

    }

    @Override
    public void addMinutesToUser(final int quantity, String userId) {
        final DatabaseReference myRef = database.getReference("Users").child(userId).child("minutes");
        getUserById(userId, new UsersRepository.GetUserByIdCallback() {
            @Override
            public void onUserRetrieved(User user) {
                myRef.setValue(user.getMinutes()+quantity);
            }

            @Override
            public void onError() {

            }
        });
    }

    private User[] parsePointsFromDataSnapshot(DataSnapshot dataSnapshot) {
        User[] users = new User[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            User user = parseUserFromSnapshot(snapshot);
            if (user != null) {
                users[index] = user;
                ++index;
            }
        }

        return users;
    }

    private User parseUserFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return usersParser.parseFromMap(key, map);
        }
        return null;
    }



}
