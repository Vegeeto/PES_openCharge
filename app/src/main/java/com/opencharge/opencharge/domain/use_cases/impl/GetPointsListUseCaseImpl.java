package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.GetPointsListUseCase;

/**
 * Created by ferran on 15/3/17.
 */

public class GetPointsListUseCaseImpl extends AbstractUseCase implements GetPointsListUseCase {

    private GetPointsListUseCase.Callback callback;
    private PointsRepository pointsRepository;

    public GetPointsListUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    PointsRepository pointsRepository,
                                    Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        String punts = pointsRepository.getPoints();
        postPoints(punts);
    }

    private void postPoints(final String points) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointsRetrieved(points);
            }
        });
    }
}
