package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Point; // Esto rompe clean arch??
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.DeletePointUseCase;

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsRepository {

    interface GetPointsCallback {
        void onPointsRetrieved(Point[] points);

        void onError();
    }

    interface GetPointByIdCallback {
        void onPointRetrieved(Point point);

        void onError();
    }

    interface CreatePointCallback {
        void onPointCreated(String id);

        void onError();
    }

    interface AddReserveToPointCallback {
        void onReserveAddedToPoint();

        void onError();
    }

    interface DeletePointCallback {
        void onPointDeleted();

        void onError();
    }

    void getPoints(final GetPointsCallback callback);

    void createPoint(Point point, final CreatePointCallback callback);

    void getPointById(String pointId, final GetPointByIdCallback callback);

    void addReserveToPoint(Reserve reserve, final AddReserveToPointCallback callback);

    void deletePoint(String pointId, final DeletePointCallback callback);

}