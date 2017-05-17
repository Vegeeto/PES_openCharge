package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.FirebaseReserve;
import com.opencharge.opencharge.domain.Entities.Reserve;

/**
 * Created by Oriol on 12/5/2017.
 */

public interface ReserveRepository {

    interface GetReservesCallback {
        void onReservesRetrieved(Reserve[] services);

        void onError();
    }

    interface GetReserveByIdCallback {
        void onPointRetrieved(Reserve service);

        void onError();
    }

    interface CreateReserveCallback {
        void onReserveCreated(String id);

        void onError();
    }

    void createReserve(String point_id, FirebaseReserve service, final CreateReserveCallback callback);

    void getReserves(String point_id, final GetReservesCallback callback);

}
