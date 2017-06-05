package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesListByPointAndDayUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.Date;

/**
 * Created by ferran on 5/6/17.
 */

public class ReservesListByPointAndDayUseCaseImpl extends AbstractUseCase implements ReservesListByPointAndDayUseCase {
    private ReservesListByPointAndDayUseCaseImpl.Callback callback;
    private ReserveRepository reserveRepository;

    private String pointId;
    private Date day;

    public ReservesListByPointAndDayUseCaseImpl(Executor threadExecutor,
                                               MainThread mainThread,
                                                ReserveRepository reserveRepository,
                                                ReservesListByPointAndDayUseCaseImpl.Callback callback) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
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
            reserveRepository.getReservesForPointAtDay(pointId, day, new ReserveRepository.GetReservesForPointAtDayCallback() {
                @Override
                public void onReservesRetrieved(Reserve[] reserves) {
                    postReserves(reserves);
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

    private void postReserves(final Reserve[] reserves) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReservesRetrieved(reserves);
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
