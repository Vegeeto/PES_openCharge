package com.opencharge.opencharge.domain.use_cases.impl;

import android.content.Context;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Factories.PointFactory;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.repository.UsersRepository;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsEditUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;

/**
 * Created by ferran on 6/6/17.
 */

public class PointsEditUseCaseImpl extends AbstractUseCase implements PointsEditUseCase {
    private PointsEditUseCase.Callback callback;
    private PointsRepository pointsRepository;

    private Point point;

    private double lat;
    private double lon;
    private String town;
    private String street;
    private String number;
    private String accessType;
    private List<String> connectorTypeList;
    private String schedule;

    public PointsEditUseCaseImpl(Executor threadExecutor,
                                 MainThread mainThread,
                                 PointsRepository pointsRepository,
                                 PointsEditUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public void setPointParameters(double lat, double lon, String town,
                                   String street, String number, String accessType,
                                   List<String> connectorTypeList, String schedule) {
        this.lat = lat;
        this.lon = lon;
        this.town = town;
        this.street = street;
        this.number = number;
        this.accessType = accessType;
        this.connectorTypeList = connectorTypeList;
        this.schedule = schedule;
    }

    @Override
    public void run() {
        point.setLat(lat);
        point.setLon(lon);
        point.setTown(town);
        point.setStreet(street);
        point.setAccessType(accessType);
        point.setConnectorTypeList(connectorTypeList);
        point.setSchedule(schedule);

        pointsRepository.savePoint(point, new PointsRepository.SavePointCallback() {
            @Override
            public void onPointSaved() {
                postSuccess();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void postSuccess() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointEdited();
            }
        });
    }
}
