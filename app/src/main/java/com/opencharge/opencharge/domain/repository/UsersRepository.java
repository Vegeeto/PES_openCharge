package com.opencharge.opencharge.domain.repository;

/**
 * Created by Usuario on 17/05/2017.
 */

public interface UsersRepository {

    public interface CreateUserCallback {
        public void onUserCreated(String id);

        public void onError();
    }

}
