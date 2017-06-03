package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User[] users = parseUsersFromDataSnapshot(dataSnapshot);
                callback.onUsersRetrieved(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
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
                callback.onError();
            }
        });
    }

    @Override
    public void getUserByEmail(String userEmail, final GetUserByEmailCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        Query query = myRef.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = parseUserFromQuerySnapshot(dataSnapshot);
                callback.onUserRetrieved(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    @Override
    public void createUser(User user, final CreateUserCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        Map<String, Object> serializedUser = usersParser.serializeUser(user);
        myRef.push().setValue(serializedUser, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                String postId = dr.getKey();
                callback.onUserCreated(postId);
            }

        });
    }

    @Override
    public void saveUser(User user, final SaveUserCallback callback) {
        DatabaseReference myRef = database.getReference("Users");
        myRef = myRef.child(user.getId());
        Map<String, Object> serializedUser = usersParser.serializeUser(user);
        myRef.setValue(serializedUser, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                callback.onUserSaved();
            }

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

    //TODO: borrar aquest mètode (també els dos anteriors d'afegir reserves). El que s'ha de fer és
    //      que el use case modifiqui el entity del user i cridi un saveUser del repository.
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

    private User[] parseUsersFromDataSnapshot(DataSnapshot dataSnapshot) {
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

    private User parseUserFromQuerySnapshot(DataSnapshot snapshot) {
        Map<String, Object> queryData = (Map<String, Object>) snapshot.getValue();
        Map.Entry<String, Object> entry = queryData.entrySet().iterator().next();

        String key = entry.getKey();
        Map<String, Object> userData = (Map<String, Object>) entry.getValue();

        return usersParser.parseFromMap(key, userData);
    }
    
}
