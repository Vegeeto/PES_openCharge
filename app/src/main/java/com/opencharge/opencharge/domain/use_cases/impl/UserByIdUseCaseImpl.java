package com.opencharge.opencharge.domain.use_cases.impl;


import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by DmnT on 18/05/2017.
 */

public class UserByIdUseCaseImpl extends AbstractUseCase implements UserByIdUseCase {


    private String userId;
    private UserByIdUseCase.Callback callback;
    private UsersRepository usersRepository;

    public UserByIdUseCaseImpl(Executor threadExecutor,
                               MainThread mainThread,
                               UsersRepository userssRepository,
                               UserByIdUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.usersRepository = usersRepository;
        this.callback = callback;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        usersRepository.getUserById(this.userId, new UsersRepository.GetUserByIdCallback() {
            @Override
            public void onUserRetrieved(User user) {
                postUser(user);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postUser(final User user) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserRetrieved(user);
            }
        });
    }
}
