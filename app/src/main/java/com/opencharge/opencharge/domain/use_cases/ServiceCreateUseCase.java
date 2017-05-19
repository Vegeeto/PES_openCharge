package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.Date;
import java.util.List;

/**
 * Created by Oriol on 9/5/2017.
 */

public interface ServiceCreateUseCase extends UseCase {
    interface Callback {
        void onServiceCreated();
        void onError();
    }

    void setServiceParameters(String pointId, Date day, Date startHour, Date endHour);
    void setRepeatMonday();
    void setRepeatTuesday();
    void setRepeatWednesday();
    void setRepeatThursday();
    void setRepeatFriday();
    void setRepeatSaturday();
    void setRepeatSunday();
    void setLastRepeat(Date lastRepeat);
}
