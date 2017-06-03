package com.opencharge.opencharge.domain.use_cases.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.opencharge.opencharge.domain.device_services.UserPreferences;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.use_cases.SetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by ferran on 3/6/17.
 */

public class SetCurrentUserUseCaseImpl extends AbstractUseCase implements SetCurrentUserUseCase {
    private String email;
    private UserPreferences userPreferences;

    public SetCurrentUserUseCaseImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     UserPreferences userPreferences) {
        super(threadExecutor, mainThread);
        this.userPreferences = userPreferences;
    }

    @Override
    public void setUserEmail(String email) {
        this.email = email;
    }

    @Override
    public void run() {
        userPreferences.setCurrentUserValue(email);
    }
}
