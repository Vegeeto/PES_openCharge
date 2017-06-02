package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.use_cases.ReservesUpdateUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Crjs on 02/06/2017.
 */

public class ReservesUpdateUseCaseImpl extends AbstractUseCase implements ReservesUpdateUseCase {
    private ReserveRepository reserveRepository;
    private Reserve reserve;

    public ReservesUpdateUseCaseImpl(Executor threadExecutor,
                                             MainThread mainThread,
                                             ReserveRepository reserveRepository) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
    }
    @Override
    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public void run() {
        reserveRepository.updateConfirmationsReserve(reserve);
    }
}
