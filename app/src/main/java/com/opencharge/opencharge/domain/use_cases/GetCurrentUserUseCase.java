package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by ferran on 4/6/17.
 */

public interface GetCurrentUserUseCase extends UseCase {
    interface Callback {
        void onCurrentUserRetrieved(User currentUser);
    }
}
