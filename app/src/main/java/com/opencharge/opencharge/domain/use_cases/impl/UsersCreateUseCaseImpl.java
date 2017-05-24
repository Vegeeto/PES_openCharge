package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.Pair;

import com.opencharge.opencharge.domain.Entities.FirebaseUser;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Factories.UserFactory;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.UsersCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.ArrayList;

/**
 * Created by Usuario on 24/05/2017.
 */

public class UsersCreateUseCaseImpl extends AbstractUseCase implements UsersCreateUseCase {
    private UsersCreateUseCase.Callback callback;
    private UsersRepository usersRepository;
    private String name;
    private String photo;
    private String email;
    private String password;
    private ArrayList<Pair<String,String>> puntsCreats;
    private ArrayList<Pair<String,String>> puntsReservats;

    public UsersCreateUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   UsersRepository usersRepository,
                                   UsersCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.usersRepository = usersRepository;
        this.callback = callback;
    }
    @Override
    public void setUserParameters(String name, String photo, String email, String password, ArrayList<Pair<String,String>> puntsCreats,
                                  ArrayList<Pair<String,String>> puntsReservats) {
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.password = password;
        this.puntsCreats = puntsCreats;
        this.puntsReservats = puntsReservats;
    }

    @Override
    public void run() {
        final User user = UserFactory.getInstance().createNewUser(name, photo, password, email, puntsCreats, puntsReservats);
        final FirebaseUser firebaseUser = UserFactory.getInstance().pointToFirebasePoint(user);
        usersRepository.createUser(firebaseUser, new UsersRepository.CreateUserCallback(){
            @Override
            public void onUserCreated(String id)
            {
                UserFactory.getInstance().setPointId(user, id);
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
