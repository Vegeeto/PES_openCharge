package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.MockUser;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UserByIdUseCase extends UseCase {
    interface Callback {
        void onUserRetrieved(MockUser user);
    }

    public void setUserId(String pointId);
}
