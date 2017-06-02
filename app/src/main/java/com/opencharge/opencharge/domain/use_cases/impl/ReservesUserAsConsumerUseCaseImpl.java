package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsSupplierUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserInvolvedUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Crjs on 02/06/2017.
 */

public class ReservesUserAsConsumerUseCaseImpl extends AbstractUseCase implements ReservesUserAsConsumerUseCase {
    private ReservesUserAsConsumerUseCase.Callback callback;
    private ReserveRepository reserveRepository;
    private String userID;

    public ReservesUserAsConsumerUseCaseImpl(Executor threadExecutor,
                                             MainThread mainThread,
                                             ReserveRepository reserveRepository,
                                             ReservesUserAsConsumerUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
        this.callback = callback;
    }
    @Override
    public void run() {
        final Calendar today = Calendar.getInstance();
        reserveRepository.getReservesAsConsumerByUserId(userID, new ReserveRepository.GetReservesByUserIdCallback() {
            @Override
            public void onReservesRetrieved(ArrayList<Reserve> reserves) {
                Reserve[] reservesVector = new Reserve[reserves.size()];
                int i = 0;
                for (Reserve r: reserves) {
                    Calendar reserveDay = Calendar.getInstance();
                    Calendar reserveTime = Calendar.getInstance();
                    reserveDay.setTime(r.getDay());
                    reserveTime.setTime(r.getEndHour());

                    //Todos estos if encadenados es para comprobar que si podemos confirmar o no
                    if(reserveDay.get(Calendar.YEAR) == today.get(Calendar.YEAR)){
                        if(reserveDay.get(Calendar.MONTH) == today.get(Calendar.MONTH)){
                            if(reserveDay.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)){
                                if(reserveTime.get(Calendar.HOUR_OF_DAY) == today.get(Calendar.HOUR_OF_DAY)){
                                    if(reserveTime.get(Calendar.MINUTE) == today.get(Calendar.MINUTE)){
                                        r.setCanConfirm(true);
                                    }
                                }
                            }
                        }
                    }
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

    private void postReserves(final Reserve[] reserves) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReservesRetrieved(reserves);
            }
        });
    }

    @Override
    public void setUserId(String userID) {
        this.userID = userID;
    }
}

