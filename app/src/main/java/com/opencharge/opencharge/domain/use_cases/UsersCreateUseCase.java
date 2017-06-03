package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.List;

/**
 * Created by Usuario on 24/05/2017.
 */

public interface UsersCreateUseCase extends UseCase {
    interface Callback {
        void onUserCreated(String id);
    }

    public void setUserParameters(String name, String photo, String email, List<UserPointSummary> points);
}
