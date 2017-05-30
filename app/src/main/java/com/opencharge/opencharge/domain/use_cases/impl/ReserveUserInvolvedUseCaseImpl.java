package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.Log;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesUserInvolvedUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.ArrayList;

/**
 * Created by Crjs on 26/05/2017.
 */

public class ReserveUserInvolvedUseCaseImpl extends AbstractUseCase implements ReservesUserInvolvedUseCase {
    private ReservesUserInvolvedUseCase.Callback callback;
    private ReserveRepository reserveRepository;
    private String userID;

    public ReserveUserInvolvedUseCaseImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          ReserveRepository reserveRepository,
                                          ReservesUserInvolvedUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
        this.callback = callback;
    }
    @Override
    public void run() {
        reserveRepository.getReservesAsSupplierByUserId(userID, new ReserveRepository.GetReservesByUserIdCallback() {
            @Override
            public void onReservesRetrieved(ArrayList<Reserve> reserves) {
                final ArrayList<Reserve> reservesResult = reserves;
                reserveRepository.getReservesAsConsumerByUserId(userID, new ReserveRepository.GetReservesByUserIdCallback() {
                    @Override
                    public void onReservesRetrieved(ArrayList<Reserve> reserves) {
                        reservesResult.addAll(reserves);
                        Reserve[] reservesVector = new Reserve[reservesResult.size()];
                        int i = 0;
                        for (Reserve r: reservesResult) {
                            reservesVector[i] = r;
                            ++i;
                        }
                        postReserves(reservesVector);
                    }

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
