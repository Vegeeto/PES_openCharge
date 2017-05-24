package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.Date;

/**
 * Created by ferran on 19/5/17.
 */

public interface ServiceListByPointAndDayUseCase extends UseCase {
    interface Callback {
        void onServicesRetrieved(Service[] services);
        void onError();
    }

    void setPointId(String pointId);
    void setDay(Date day);
}
