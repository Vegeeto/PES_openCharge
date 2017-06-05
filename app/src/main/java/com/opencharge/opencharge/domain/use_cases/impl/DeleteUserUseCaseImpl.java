package com.opencharge.opencharge.domain.use_cases.impl;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.use_cases.DeleteUserUseCase;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * Created by Usuario on 05/06/2017.
 */

public class DeleteUserUseCaseImpl extends AbstractUseCase implements DeleteUserUseCase {
    private Context context;

    public DeleteUserUseCaseImpl(Executor threadExecutor, MainThread mainThread, Context context) {
        super(threadExecutor, mainThread);
        this.context = context;
    }

    @Override
    public void run() {
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        GetCurrentUserUseCase getCreateUsersUseCase = useCasesLocator.getGetCurrentUserUseCase(context, new GetCurrentUserUseCase.Callback() {
            @Override
            public void onCurrentUserRetrieved(User currentUser) {
                currentUser.getPoints();
                FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getId()).removeValue();
            }
        });
        getCreateUsersUseCase.execute();

    }
}
