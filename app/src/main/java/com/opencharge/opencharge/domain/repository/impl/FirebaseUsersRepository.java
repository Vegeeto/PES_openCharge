package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.MockUser;
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
    public void getUserById(String userId, final GetUserByIdCallback callback) {
        DatabaseReference myRef = database.getReference("Userss").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MockUser user = parseUserFromSnapshot(dataSnapshot);
                callback.onUserRetrieved(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });
    }

    private MockUser parseUserFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return usersParser.parseFromMap(key, map);
        }
        return null;
    }


}
