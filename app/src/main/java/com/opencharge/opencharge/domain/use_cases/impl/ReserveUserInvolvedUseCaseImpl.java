package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesListUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Crjs on 26/05/2017.
 */

public class ReserveListUseCaseImpl extends AbstractUseCase implements ReservesListUseCase {
    private ReservesListUseCase.Callback callback;
    private ReserveRepository reserveRepository;
    private String userID;

    public ReserveListUseCaseImpl(Executor threadExecutor,
                                 MainThread mainThread,
                                  ReserveRepository reserveRepository,
                                  ReservesListUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
        this.callback = callback;
    }
    @Override
    public void run() {
        reserveRepository.getReservesAsSupplierByUserId(userID, new ReserveRepository.GetReservesCallback() {

            @Override
            public void onReservesRetrieved(Reserve[] reserves) {
                reserveRepository.getReservesAsConsumerByUserId(userID, new ReserveRepository.GetReservesCallback() {

                    @Override
                    public void onReservesRetrieved(Reserve[] reserves) {postReserves(reserves);}

                    @Override
                    public void onError() {

                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void setPointParameters(String userID) {
        this.userID = userID;
;    }

    private void postReserves(final Reserve[] reserves) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReservesRetrieved(reserves);
            }
        });
    }
}
