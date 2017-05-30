package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.List;

/**
 * Created by Crjs on 26/05/2017.
 */

public interface ReservesUserInvolvedUseCase extends UseCase {
    interface Callback {
        void onReservesRetrieved(Reserve[] reserves);
    }
    void setPointParameters(String userID);
}
