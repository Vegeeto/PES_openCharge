package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Oriol on 24/5/2017.
 */

public interface UsersListUseCase extends UseCase {
    interface Callback {
        void onUsersRetrieved(User[] users);
    }
}
