package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by ferran on 3/6/17.
 */

public interface SetCurrentUserUseCase extends UseCase {
    void setUserEmail(String email);
}
