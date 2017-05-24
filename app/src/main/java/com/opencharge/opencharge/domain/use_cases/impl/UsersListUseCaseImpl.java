package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.UsersCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.UsersListUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Oriol on 24/5/2017.
 */

public class UsersListUseCaseImpl extends AbstractUseCase implements UsersListUseCase {

    private UsersListUseCase.Callback callback;
    private UsersRepository usersRepository;

    public UsersListUseCaseImpl(Executor threadExecutor,
                                MainThread mainThread,
                                UsersRepository usersRepository,
                                Callback callback) {
        super(threadExecutor, mainThread);

        this.usersRepository = usersRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        usersRepository.getUsers(new UsersRepository.GetUsersCallback() {
            @Override
            public void onUsersRetrieved(User[] users) {
                postUsers(users);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postUsers(final User[] users) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUsersRetrieved(users);
            }
        });
    }
}
