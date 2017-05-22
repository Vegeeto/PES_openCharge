package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Usuario on 17/05/2017.
 */

public interface LoginUseCase extends UseCase {
    interface Callback {
        void onUserAdded (String id);
    }
}
