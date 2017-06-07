package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Usuario on 05/06/2017.
 */

public interface DeleteUserUseCase extends UseCase {
    interface Callback {
        void userDeleted();
    }
}
