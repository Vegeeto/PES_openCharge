package com.opencharge.opencharge.presentation.locators;

import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.repository.impl.FirebasePointsRepository;

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
}
