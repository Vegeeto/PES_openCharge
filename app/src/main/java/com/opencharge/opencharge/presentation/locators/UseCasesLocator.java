package com.opencharge.opencharge.presentation.locators;

import android.content.Context;

import com.opencharge.opencharge.domain.device_services.UserPreferences;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.ServiceListByPointAndDayUseCase;
import com.opencharge.opencharge.domain.use_cases.SetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.UsersCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.UsersListUseCase;
import com.opencharge.opencharge.domain.use_cases.impl.CommentsListUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.PointsCreateUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;
import com.opencharge.opencharge.domain.use_cases.impl.PointByIdUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.PointsListUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ReserveCreateUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ReserveUserInvolvedUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ReservesUpdateConfirmationsUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ReservesUserAsConsumerUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ReservesUserAsSupplierUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ServiceCreateUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.ServiceListByPointAndDayUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.SetCurrentUserUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.UserByIdUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.UserLocationUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.AddCommentUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.UsersCreateUseCaseImpl;
import com.opencharge.opencharge.domain.use_cases.impl.UsersListUseCaseImpl;

/**
 * Created by ferran on 22/3/17.
 */

public class UseCasesLocator {
    private static UseCasesLocator instance;

    private UseCasesLocator() {
    }

    public static UseCasesLocator getInstance() {
        if (instance == null) {
            instance = new UseCasesLocator();
        }
        return instance;
    }

    public PointsListUseCase getPointsListUseCase(PointsListUseCase.Callback callback) {
        return new PointsListUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getPointsRepository(),
                callback
        );
    }

    public UsersListUseCase getUsersListUseCase(UsersListUseCase.Callback callback) {
        return new UsersListUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getUsersRepository(),
                callback
        );
    }

    public PointByIdUseCase getPointByIdUseCase(PointByIdUseCase.Callback callback) {
        return new PointByIdUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getPointsRepository(),
                callback
        );
    }

    public UserLocationUseCase getUserLocationUseCase(Context context, UserLocationUseCase.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new UserLocationUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                sl.getUserLocationService(context),
                callback
        );
    }

    public PointsCreateUseCase getPointsCreateUseCase(PointsCreateUseCase.Callback callback) {
        return new PointsCreateUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getPointsRepository(),
                callback);
    }

    public UsersCreateUseCase getUsersCreateUseCase(UsersCreateUseCase.Callback callback) {
        return new UsersCreateUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getUsersRepository(),
                callback);
    }

    public AddCommentUseCase getAddCommentUseCase(AddCommentUseCase.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new AddCommentUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getCommentsRepository(),
                callback
        );
    }

    public UserByIdUseCase getUserByIdUseCase(UserByIdUseCase.Callback callback) {
        return new UserByIdUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getUsersRepository(),
                callback
        );
    }

    public CommentsListUseCase getCommentsListUseCase(CommentsListUseCase.Callback callback) {
        return new CommentsListUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getCommentsRepository(),
                callback
        );
    }

    public ServiceCreateUseCase getServiceCreateUseCase(ServiceCreateUseCase.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ServiceCreateUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getServiceRepository(),
                callback
        );
    }

    public ServiceListByPointAndDayUseCase getServiceListByPointAndDayUseCase(ServiceListByPointAndDayUseCase.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ServiceListByPointAndDayUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getServiceRepository(),
                callback
        );
    }

    public ReserveCreateUseCase getReserveCreateUseCase(ReserveCreateUseCase.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ReserveCreateUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getReserveRepository(),
                RepositoriesLocator.getInstance().getUsersRepository(),
                callback
        );
    }

    public ReserveUserInvolvedUseCaseImpl getReservesUserInvolvedUseCaseImpl(ReserveUserInvolvedUseCaseImpl.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ReserveUserInvolvedUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getReserveRepository(),
                callback
        );
    }

    public ReservesUserAsSupplierUseCaseImpl getReservesUserAsSupplierUseCaseImpl(ReservesUserAsSupplierUseCaseImpl.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ReservesUserAsSupplierUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getReserveRepository(),
                callback
        );
    }

    public ReservesUserAsConsumerUseCaseImpl getReservesUserAsConsumerUseCaseImpl(ReservesUserAsConsumerUseCaseImpl.Callback callback) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ReservesUserAsConsumerUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getReserveRepository(),
                callback
        );
    }

    public ReservesUpdateConfirmationsUseCaseImpl getReservesUpdateUseCaseImpl() {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new ReservesUpdateConfirmationsUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                RepositoriesLocator.getInstance().getReserveRepository(),
                RepositoriesLocator.getInstance().getUsersRepository());
    }

    public SetCurrentUserUseCase getSetCurrentUserUseCase(Context context) {
        ServicesLocator sl = ServicesLocator.getInstance();
        return new SetCurrentUserUseCaseImpl(
                sl.getExecutor(),
                sl.getMainThread(),
                sl.getUserPreferencesService(context));
    }

}
