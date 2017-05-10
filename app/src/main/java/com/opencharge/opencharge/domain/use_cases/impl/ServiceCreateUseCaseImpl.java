package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.List;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ServiceCreateUseCaseImpl extends AbstractUseCase implements ServiceCreateUseCase {

    private ServiceCreateUseCase.Callback callback;

    public ServiceCreateUseCaseImpl(Executor threadExecutor, MainThread mainThread, ServiceCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.callback = callback;
    }

    @Override
    public void setServiceParameters(long date, long startTime, long endTime, List<String> repeats, long endRepeat) {

    }

    @Override
    public void run() {

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
