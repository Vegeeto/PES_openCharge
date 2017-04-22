package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by ferran on 21/4/17.
 */

public class PointByIdUseCaseImpl extends AbstractUseCase implements PointByIdUseCase {

    private String pointId;
    private PointByIdUseCase.Callback callback;
    private PointsRepository pointsRepository;

    public PointByIdUseCaseImpl(Executor threadExecutor,
                                MainThread mainThread,
                                PointsRepository pointsRepository,
                                PointByIdUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    @Override
    public void run() {
        pointsRepository.getPointById(this.pointId, new PointsRepository.GetPointByIdCallback() {
            @Override
            public void onPointRetrieved(Point point) {
                postPoint(point);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postPoint(final Point point) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointRetrieved(point);
            }
        });
    }
}
