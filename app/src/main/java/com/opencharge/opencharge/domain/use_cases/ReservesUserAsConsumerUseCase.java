package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Crjs on 02/06/2017.
 */

public interface ReservesUserAsConsumerUseCase extends UseCase {

    interface Callback {
        void onReservesRetrieved(Reserve[] reserves);
    }
    void setUserId(String userID);
}
