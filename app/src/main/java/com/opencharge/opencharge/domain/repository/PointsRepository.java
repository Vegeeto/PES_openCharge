package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Points; // Esto rompe clean arch??

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsRepository {

    public interface GetPointsCallback {
        public void onPointsRetrieved(Points[] points);
        public void onError();
    }

    void getPoints(final GetPointsCallback callback);
}
