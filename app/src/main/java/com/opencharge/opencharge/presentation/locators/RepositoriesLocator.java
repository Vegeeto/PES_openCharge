package com.opencharge.opencharge.presentation.locators;

import com.opencharge.opencharge.domain.repository.CommentsRepository;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseCommentsRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebasePointsRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseUsersRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseReserveRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebaseServiceRepository;

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

    public CommentsRepository getCommentsRepository() {
        return new FirebaseCommentsRepository();
    }

    public ServiceRepository getServiceRepository() {
        return new FirebaseServiceRepository();
    }

    public ReserveRepository getReserveRepository() {
        return new FirebaseReserveRepository();
    }

    public UsersRepository getUsersRepository() { 
        return new FirebaseUsersRepository(); 
    }
}
