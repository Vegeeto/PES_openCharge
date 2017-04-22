package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Point; // Esto rompe clean arch??

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsRepository {

    public interface GetPointsCallback {
        public void onPointsRetrieved(Point[] points);
        public void onError();
    }

    public interface GetPointByIdCallback {
        public void onPointRetrieved(Point point);
        public void onError();
    }

    void getPoints(final GetPointsCallback callback);
    void getPointById(String pointId, final GetPointByIdCallback callback);
}
