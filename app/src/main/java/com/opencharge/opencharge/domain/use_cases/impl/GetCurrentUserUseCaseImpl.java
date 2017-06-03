package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.device_services.UserPreferences;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;

/**
 * Created by ferran on 4/6/17.
 */

public class GetCurrentUserUseCaseImpl extends AbstractUseCase implements GetCurrentUserUseCase {
    private UserPreferences userPreferences;
    private UsersRepository usersRepository;
    private GetCurrentUserUseCase.Callback callback;

    public GetCurrentUserUseCaseImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     UserPreferences userPreferences,
                                     UsersRepository usersRepository,
                                     GetCurrentUserUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.userPreferences = userPreferences;
        this.usersRepository = usersRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        String currentUserEmail = userPreferences.getCurrentUserValue();
        boolean existsCurrentUser = (currentUserEmail != null & currentUserEmail.length() > 0);
        if (existsCurrentUser) {
            usersRepository.getUserByEmail(currentUserEmail, new UsersRepository.GetUserByEmailCallback() {
                @Override
                public void onUserRetrieved(User user) {
                    postCurrentUser(user);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    private void postCurrentUser(final User currentUser) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCurrentUserRetrieved(currentUser);
            }
        });
    }

}
