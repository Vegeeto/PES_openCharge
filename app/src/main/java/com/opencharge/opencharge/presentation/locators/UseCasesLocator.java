package com.opencharge.opencharge.presentation.locators;

import android.content.Context;
import android.util.Log;

import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
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
        if(instance == null) {
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
                RepositoriesLocator.getInstance().getCommnetsRepository(),
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
                RepositoriesLocator.getInstance().getCommnetsRepository(),
                callback
        );
    }
}
