package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.Date;

/**
 * Created by ferran on 5/6/17.
 */

public interface ReservesListByPointAndDayUseCase extends UseCase {
    interface Callback {
        void onReservesRetrieved(Reserve[] reserves);
        void onError();
    }

    void setPointId(String pointId);
    void setDay(Date day);
}
