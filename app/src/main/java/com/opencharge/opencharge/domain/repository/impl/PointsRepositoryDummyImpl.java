package com.opencharge.opencharge.domain.repository.impl;

import com.opencharge.opencharge.domain.repository.PointsRepository;

/**
 * Created by ferran on 15/3/17.
 */

public class PointsRepositoryDummyImpl implements PointsRepository {
    @Override
    public String getPoints() {
        String punts = "punt1, punt2, punt3";

        // let's simulate some network/database lag
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return punts;
    }
}
