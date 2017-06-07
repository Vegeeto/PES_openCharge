package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Reserve;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Oriol on 12/5/2017.
 */

public interface ReserveRepository {

    interface GetReservesForPointAtDayCallback {
        void onReservesRetrieved(Reserve[] services);

        void onError();
    }

    interface GetReservesByUserIdCallback {
        void onReservesRetrieved(ArrayList<Reserve> reserves);

        void onError();
    }

    interface GetReserveByIdCallback {
        void onReserveRetrieved(Reserve reserve);

        void onError();
    }

    interface CreateReserveCallback {
        void onReserveCreated(String reserveId);

        void onError();
    }

    void createReserve(Reserve reserve, final CreateReserveCallback callback);

    void getReservesForPointAtDay(String point_id, Date day, final GetReservesForPointAtDayCallback callback);

    void getReservesAsSupplierByUserId(String userId, final GetReservesByUserIdCallback callback);

    void getReservesAsConsumerByUserId(String userId, final GetReservesByUserIdCallback callback);

    void getReserveById(String reserveId, final GetReserveByIdCallback callback);

    void updateConfirmationsReserve(Reserve r);

    void updateStateReserve(Reserve r);

}
