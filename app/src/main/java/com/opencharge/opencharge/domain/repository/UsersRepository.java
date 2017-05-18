package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.MockUser;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UsersRepository {

    public interface GetUserByIdCallback {
        public void onUserRetrieved(MockUser user);

        public void onError();
    }

    void getUserById(String userId, final GetUserByIdCallback callback);
}