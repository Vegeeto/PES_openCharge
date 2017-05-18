package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ServiceCreateUseCaseImpl extends AbstractUseCase implements ServiceCreateUseCase {

    private ServiceCreateUseCase.Callback callback;
    private ServiceRepository serviceRepository;

    private Date day;
    private Date startHour;
    private Date endHour;
    private Map<Integer, Boolean> repeat;
    private Date lastRepeat;



    public ServiceCreateUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    ServiceRepository serviceRepository,
                                    ServiceCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.serviceRepository = serviceRepository;
        this.callback = callback;
        this.repeat = new HashMap<>();
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

    @Override
    public void setServiceParameters(Date day, Date startHour, Date endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public void setRepeatMonday() {
        repeat.put(0, true);
    }

    @Override
    public void setRepeatTuesday() {
        repeat.put(1, true);
    }

    @Override
    public void setRepeatWednesday() {
        repeat.put(2, true);
    }

    @Override
    public void setRepeatThursday() {
        repeat.put(3, true);
    }

    @Override
    public void setRepeatFriday() {
        repeat.put(4, true);
    }

    @Override
    public void setRepeatSaturday() {
        repeat.put(5, true);
    }

    @Override
    public void setRepeatSunday() {
        repeat.put(6, true);
    }

    @Override
    public void setLastRepeat(Date lastRepeat) {
        this.lastRepeat = lastRepeat;
    }
}
