package com.opencharge.opencharge.domain.use_cases.impl;

import android.location.Location;

import com.opencharge.opencharge.domain.device_services.UserLocationService;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Oriol on 23/3/2017.
 * Classe de gestió de la localització de l'usuari
 */

public class UserLocationUseCaseImpl extends AbstractUseCase implements UserLocationUseCase, UserLocationService.Callback {

    private UserLocationService userLocationService;
    private UserLocationUseCase.Callback callback;

    public UserLocationUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   UserLocationService userLocationService,
                                   UserLocationUseCase.Callback callback) {
        super(threadExecutor, mainThread);
        this.userLocationService = userLocationService;
        this.callback = callback;
    }

    @Override
    public void run() {
        userLocationService.getUserLocation(this);
    }

    @Override
    public void onLocationRetrieved(final Location location) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationRetrieved(location);
            }
        });
    }

    @Override
    public void onCanNotGetLocationError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCanNotGetLocationError();
            }
        });
    }
}