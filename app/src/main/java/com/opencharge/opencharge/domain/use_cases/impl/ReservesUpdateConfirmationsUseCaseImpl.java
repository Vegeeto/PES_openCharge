package com.opencharge.opencharge.domain.use_cases.impl;

import android.os.Debug;
import android.util.Log;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesUpdateConfirmationsUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by Crjs on 02/06/2017.
 */

public class ReservesUpdateConfirmationsUseCaseImpl extends AbstractUseCase implements ReservesUpdateConfirmationsUseCase {
    private ReserveRepository reserveRepository;
    private UsersRepository usersRepository;
    private Reserve reserve;

    public ReservesUpdateConfirmationsUseCaseImpl(Executor threadExecutor,
                                                  MainThread mainThread,
                                                  ReserveRepository reserveRepository,
                                                  UsersRepository usersRepository) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
        this.usersRepository = usersRepository;
    }
    @Override
    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public void run() {
        reserveRepository.updateConfirmationsReserve(reserve);
        if(reserve.isMarkedAsFinishedByOwner() && reserve.isMarkedAsFinishedByUser()) {
            reserve.accept();
            DateTime endHour = new DateTime(reserve.getEndHour());
            DateTime startHour = new DateTime(reserve.getStartHour());
            long diffInMillis = endHour.getMillis() - startHour.getMillis();
            int minutes = (int) ((diffInMillis / (1000*60)) % 60);
            int hours   = (int) ((diffInMillis / (1000*60*60)) % 24);
            usersRepository.addMinutesToUser(-(minutes+hours*60), reserve.getConsumerUserId());
            usersRepository.addMinutesToUser((minutes+hours*60), reserve.getSupplierUserId());
        }
    }
}
