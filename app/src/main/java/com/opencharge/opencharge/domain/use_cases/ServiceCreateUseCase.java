package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.List;

/**
 * Created by Oriol on 9/5/2017.
 */

public interface ServiceCreateUseCase extends UseCase {
    interface Callback {
        void onServiceCreated(String id);
    }

    public void setServiceParameters(long date, long startTime, long endTime, List<String> repeats, long endRepeat);


}
