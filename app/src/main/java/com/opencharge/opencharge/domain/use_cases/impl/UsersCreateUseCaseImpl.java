package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.Factories.UserFactory;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.UsersCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.List;

/**
 * Created by Usuario on 24/05/2017.
 */

public class UsersCreateUseCaseImpl extends AbstractUseCase implements UsersCreateUseCase {
    private UsersCreateUseCase.Callback callback;
    private UsersRepository usersRepository;
    private String name;
    private String photo;
    private String email;
    private List<UserPointSummary> points;

    public UsersCreateUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   UsersRepository usersRepository,
                                   UsersCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.usersRepository = usersRepository;
        this.callback = callback;
    }
    @Override
    public void setUserParameters(String name, String photo, String email, List<UserPointSummary> points) {
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.points = points;
    }

    @Override
    public void run() {
        final User user = UserFactory.getInstance().createNewUser(name, photo, email, points);
        usersRepository.createUser(user, new UsersRepository.CreateUserCallback(){
            @Override
            public void onUserCreated(String id)
            {
                user.setId(id);
                postUser(id);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postUser(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserCreated(id);
            }
        });
    }

}
