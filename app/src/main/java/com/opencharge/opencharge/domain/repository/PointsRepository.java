package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Point;

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsRepository {

    public interface GetPointsCallback {
        public void onPointsRetrieved(Point[] points);
        public void onError();
    }

    public interface GetCreatePointCallback {
        public void onPointCreated(Point point);
        public void onError();
    }

    void getPoints(final GetPointsCallback callback);
    String createPoint(Point p);
}
