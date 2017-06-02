package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsSupplierUseCase;

/**
 * Created by Crjs on 02/06/2017.
 */

public class ReserveConfirmAsSupplierUseCaseImpl extends AbstractUseCase implements ReserveConfirmAsSupplierUseCase {
    private ReserveRepository reserveRepository;
    private Reserve reserve;

    public ReserveConfirmAsSupplierUseCaseImpl(Executor threadExecutor,
                                                  MainThread mainThread,
                                                  ReserveRepository reserveRepository,
                                                  UsersRepository usersRepository) {
        super(threadExecutor, mainThread);

        this.reserveRepository = reserveRepository;
    }
    @Override
    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public void run() {
        reserve.isMarkedAsFinishedBySupplier();
    }
}
