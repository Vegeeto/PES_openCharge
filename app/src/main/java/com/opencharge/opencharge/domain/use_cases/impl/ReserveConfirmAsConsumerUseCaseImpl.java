package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsSupplierUseCase;

import org.joda.time.DateTime;

/**
 * Created by Crjs on 02/06/2017.
 */

public class ReserveConfirmAsConsumerUseCaseImpl extends AbstractUseCase implements ReserveConfirmAsConsumerUseCase{
    private ReserveRepository reserveRepository;
    private UsersRepository usersRepository;

    private Reserve reserve;

    public ReserveConfirmAsConsumerUseCaseImpl(Executor threadExecutor,
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
        reserve.markAsFinishedByConsumer();
        if(reserve.isMarkedAsFinishedByConsumer() && reserve.isMarkedAsFinishedBySupplier()) {
            reserve.accept();
            DateTime endHour = new DateTime(reserve.getEndHour());
            DateTime startHour = new DateTime(reserve.getStartHour());
            long diffInMillis = endHour.getMillis() - startHour.getMillis();
            int minutes = (int) ((diffInMillis / (1000*60)) % 60);
            int hours   = (int) ((diffInMillis / (1000*60*60)) % 24);
            usersRepository.addMinutesToUser(-(minutes+hours*60), reserve.getConsumerUserId());
            usersRepository.addMinutesToUser((minutes+hours*60), reserve.getSupplierUserId());
        }
        reserveRepository.updateConfirmationsReserve(reserve);
    }
}
