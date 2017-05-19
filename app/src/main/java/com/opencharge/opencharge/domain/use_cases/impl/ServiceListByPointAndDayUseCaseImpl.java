package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.SparseBooleanArray;

import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.ServiceListByPointAndDayUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.Date;

/**
 * Created by ferran on 19/5/17.
 */

public class ServiceListByPointAndDayUseCaseImpl extends AbstractUseCase implements ServiceListByPointAndDayUseCase {
    private ServiceListByPointAndDayUseCase.Callback callback;
    private ServiceRepository serviceRepository;

    private String pointId;
    private Date day;

    public ServiceListByPointAndDayUseCaseImpl(Executor threadExecutor,
                                               MainThread mainThread,
                                               ServiceRepository serviceRepository,
                                               ServiceListByPointAndDayUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.serviceRepository = serviceRepository;
        this.callback = callback;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @Override
    public void run() {
        if (pointId != null && day != null) {
            serviceRepository.getServicesForPointAtDay(pointId, day, new ServiceRepository.GetServicesForPointAtDayCallback() {
                @Override
                public void onServicesRetrieved(Service[] services) {
                    postServices(services);
                }

                @Override
                public void onError() {
                    postError();
                }
            });
        }
        else {
            postError();
        }
    }

    private void postServices(final Service[] services) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onServicesRetrieved(services);
            }
        });
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }
}
