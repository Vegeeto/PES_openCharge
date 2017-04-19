package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Point;
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
    double lat;
    double lon;
    String town;
    String street;
    String number;
    String accesType;
    String connectorType;
    String schedule;

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
        //Crida factoryPoints
        //Crear Point
        Point p = new Point();
        pointsRepository.createPoint();
    }

    private void postPoints(final Point point) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointCreated(point);
            }
        });
    }
}
