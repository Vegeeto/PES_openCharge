package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Reserve;

import java.util.Date;

/**
 * Created by ferran on 5/6/17.
 */

public interface ReservesListByPointAndDayUseCase {
    interface Callback {
        void onReservesRetrieved(Reserve[] services);
        void onError();
    }

    void setPointId(String pointId);
    void setDay(Date day);
}
