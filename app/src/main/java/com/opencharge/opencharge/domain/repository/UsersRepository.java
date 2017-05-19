package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.User;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UsersRepository {

    public interface GetUserByIdCallback {
        public void onUserRetrieved(User user);

        public void onError();
    }

    void getUserById(String userId, final GetUserByIdCallback callback);
}