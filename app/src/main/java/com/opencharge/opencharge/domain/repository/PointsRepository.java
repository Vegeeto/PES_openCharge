package com.opencharge.opencharge.domain.repository;

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsRepository {

    public interface GetPointsCallback {
        public void onPointsRetrieved(String points);

        public void onError();
    }

    void getPoints(final GetPointsCallback callback);
}
