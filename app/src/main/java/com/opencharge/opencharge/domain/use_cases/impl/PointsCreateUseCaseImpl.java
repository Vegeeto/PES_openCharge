package com.opencharge.opencharge.domain.use_cases.impl;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Factories.PointFactory;
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
    private double lat;
    private double lon;
    private String town;
    private String street;
    private String number;
    private String accessType;
    private String connectorType;
    private String schedule;

    public PointsCreateUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   PointsRepository pointsRepository,
                                   PointsCreateUseCase.Callback callback,
                                   double lat,
                                   double lon,
                                   String town,
                                   String street,
                                   String number,
                                   String accessType,
                                   String connectorType,
                                   String schedule) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
        this.town = town;
        this.street = street;
        this.number = number;
        this.accessType = accessType;
        this.connectorType = connectorType;
        this.schedule =  schedule;
    }

    @Override
    public void run() {
        System.out.println("Enter PointsCreate.run()");
        Point point = PointFactory.getInstance().createNewPoint(lat,lon,town,street,number,accessType,connectorType,schedule);
        System.out.println("Created Point: "+point.toString());
        String id = pointsRepository.createPoint(point); //No entra dintre de la funció?
        System.out.println("Id returned from firebase: "+id);
        PointFactory.getInstance().setPointId(point, id);
        postPoints(point);
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
