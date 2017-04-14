package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Points;  //Esto rompe clean arch?
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;

/**
 * Created by ferran on 15/3/17.
 */

public class PointsListUseCaseImpl extends AbstractUseCase implements PointsListUseCase {

    private PointsListUseCase.Callback callback;
    private PointsRepository pointsRepository;

    public PointsListUseCaseImpl(Executor threadExecutor,
                                 MainThread mainThread,
                                 PointsRepository pointsRepository,
                                 Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        pointsRepository.getPoints(new PointsRepository.GetPointsCallback() {
            @Override
            public void onPointsRetrieved(Points[] points) {
                postPoints(points);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postPoints(final Points[] points) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointsRetrieved(points);
            }
        });
    }
}
