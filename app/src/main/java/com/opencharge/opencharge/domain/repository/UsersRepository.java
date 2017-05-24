package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.FirebaseUser;
import com.opencharge.opencharge.domain.Entities.User;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UsersRepository {

    public interface GetUsersCallback {
        public void onUsersRetrieved(User[] users);

        public void onError();
    }

     public interface CreateUserCallback {
        public void onUserCreated(String id);

        public void onError();
    }

    public interface GetUserByIdCallback {
        public void onUserRetrieved(User user);

        public void onError();
    }

    void getUsers(final UsersRepository.GetUsersCallback callback);

    void getUserById(String userId, final GetUserByIdCallback callback);

    void createUser(FirebaseUser user, final CreateUserCallback callback);
}
