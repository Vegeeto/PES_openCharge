package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Points;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Crjs on 16/04/2017.
 */

public class PointsCreateUseCaseImpl extends AbstractUseCase implements PointsCreateUseCase {
    private PointsCreateUseCase.Callback callback;
    private PointsRepository pointsRepository;

    public PointsCreateUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   PointsRepository pointsRepository,
                                   PointsCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        pointsRepository.createPoint(new PointsRepository.GetCreatePointCallback() {
            @Override
            public void onPointCreated(Points point) {
                postPoints(point);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postPoints(final Points point) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointCreated(point);
            }
        });
    }
}
