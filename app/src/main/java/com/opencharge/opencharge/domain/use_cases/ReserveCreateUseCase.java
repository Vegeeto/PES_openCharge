package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Oriol on 9/5/2017.
 */

public interface ReserveCreateUseCase extends UseCase {
    interface Callback {
        void onReserveCreated(String id);
        void onError();
    }

    void setReserveParameters(long date, long startTime, long endTime);


}
