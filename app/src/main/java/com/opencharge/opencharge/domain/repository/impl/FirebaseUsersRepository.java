package com.opencharge.opencharge.domain.repository.impl;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.repository.UsersRepository;

/**
 * Created by Usuario on 17/05/2017.
 */

public class FirebaseUsersRepository implements UsersRepository{
    private FirebaseDatabase database;

    public FirebaseUsersRepository() {
        this.database = FirebaseDatabase.getInstance();
    }



}
