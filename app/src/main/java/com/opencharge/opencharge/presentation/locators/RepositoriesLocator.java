package com.opencharge.opencharge.presentation.locators;

import android.util.Log;

import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseCommentsRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebasePointsRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseUsersRepository;

/**
 * Created by ferran on 22/3/17.
 */

public class RepositoriesLocator {
    private static RepositoriesLocator instance;

    private RepositoriesLocator() {
    }

    public static RepositoriesLocator getInstance() {
        if(instance == null) {
            instance = new RepositoriesLocator();
        }
        return instance;
    }

    public PointsRepository getPointsRepository() {
        return new FirebasePointsRepository();
    }

    public CommentsRepository getCommnetsRepository() {
        return new FirebaseCommentsRepository();
    }

    public UsersRepository getUsersRepository() { return new FirebaseUsersRepository(); }
}
