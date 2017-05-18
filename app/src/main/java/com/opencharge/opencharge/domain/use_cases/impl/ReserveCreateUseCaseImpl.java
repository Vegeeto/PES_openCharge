package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.use_cases.ReserveCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ReserveCreateUseCaseImpl extends AbstractUseCase implements ReserveCreateUseCase {

    private Callback callback;
    private ReserveRepository reserveRepository;
    private String reserve_id;
    private String date;
    private String startTime;
    private String endTime;

    public ReserveCreateUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    ReserveRepository reserveRepository,
                                    Callback callback) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
        this.callback = callback;
    }

    @Override
    public void setReserveParameters(long date, long startTime, long endTime) {
        //TODO: implement method
    }

    @Override
    public void run() {
        //TODO: implement method
    }

    private void postReserve(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReserveCreated(id);
            }
        });
    }

}
