package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.FirebaseUser;
import com.opencharge.opencharge.domain.Entities.User;

/**
 * Created by DmnT on 18/05/2017.
 */

public interface UsersRepository {

     interface GetUsersCallback {
         void onUsersRetrieved(User[] users);

         void onError();
    }

     interface CreateUserCallback {
        void onUserCreated(String id);

        void onError();
    }

    interface GetUserByIdCallback {
        void onUserRetrieved(User user);

        void onError();
    }

    interface AddReserveToUser {
        void onReserveAdded();
        void onError();
    }

    void getUsers(final UsersRepository.GetUsersCallback callback);

    void getUserById(String userId, final GetUserByIdCallback callback);

    void createUser(FirebaseUser user, final CreateUserCallback callback);

    void addSupplyReserveToUser(String reserveId, String userId, AddReserveToUser callback);
    void addConsumerReserveToUser(String reserveId, String userId, AddReserveToUser callback);
}
