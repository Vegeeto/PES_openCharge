package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.List;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ServiceCreateUseCaseImpl extends AbstractUseCase implements ServiceCreateUseCase {

    private ServiceCreateUseCase.Callback callback;
    private ServiceRepository serviceRepository;
    private String service_id;
    private String date;
    private String startTime;
    private String endTime;
    //TODO: Add more parameters

    public ServiceCreateUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    ServiceRepository serviceRepository,
                                    ServiceCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.serviceRepository = serviceRepository;
        this.callback = callback;
    }

    @Override
    public void setServiceParameters(long date, long startTime, long endTime, List<String> repeats, long endRepeat) {
        //TODO: implement method
    }

    @Override
    public void run() {
        //TODO: implement method
    }

    private void postService(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onServiceCreated(id);
            }
        });
    }

}
