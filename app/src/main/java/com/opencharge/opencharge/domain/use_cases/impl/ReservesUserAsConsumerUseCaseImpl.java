package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsSupplierUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserInvolvedUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
        final DateTime today = new DateTime();
        reserveRepository.getReservesAsConsumerByUserId(userID, new ReserveRepository.GetReservesByUserIdCallback() {
            @Override
            public void onReservesRetrieved(ArrayList<Reserve> reserves) {
                Reserve[] reservesVector = new Reserve[reserves.size()];
                int i = 0;
                for (Reserve r: reserves) {

                    DateTime reserveTime = new DateTime().withDate(new LocalDate(r.getDay()))
                            .withTime(new LocalTime(r.getStartHour()));

                    if (today.isAfter(reserveTime)) {
                        r.setCanConfirm(true);
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

