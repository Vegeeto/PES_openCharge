package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ReserveRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.ReserveCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ReserveCreateUseCaseImpl extends AbstractUseCase implements ReserveCreateUseCase {

    private Callback callback;
    private ReserveRepository reserveRepository;
    private UsersRepository usersRepository;
    private Reserve reserve;

    public ReserveCreateUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    ReserveRepository reserveRepository,
                                    UsersRepository usersRepository,
                                    Callback callback) {
        super(threadExecutor, mainThread);

        this.usersRepository = usersRepository;
        this.reserveRepository = reserveRepository;
        this.callback = callback;
    }

    @Override
    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public void run() {
        this.reserveRepository.createReserve(reserve, new ReserveRepository.CreateReserveCallback() {
            @Override
            public void onReserveCreated(String reserveId) {
                usersRepository.addConsumerReserveToUser(reserveId, reserve.getConsumerUserId(), new UsersRepository.AddReserveToUser() {
                    @Override
                    public void onReserveAdded() {
                        //TODO get id of the owner of the point where the reserve is created and add a SupplyReserve to him
                    }

                    @Override
                    public void onError() {
                        postError();
                    }
                });
            }

            @Override
            public void onError() {
                postError();
            }
        });
    }

    private void postReserve(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReserveCreated(id);
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
