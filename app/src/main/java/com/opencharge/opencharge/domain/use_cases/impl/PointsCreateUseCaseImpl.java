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
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;

/**
 * Created by Crjs on 16/04/2017.
 */

public class PointsCreateUseCaseImpl extends AbstractUseCase implements PointsCreateUseCase {
    private PointsCreateUseCase.Callback callback;
    private PointsRepository pointsRepository;
    private UsersRepository usersRepository;
    private Context context;

    private double lat;
    private double lon;
    private String town;
    private String street;
    private String number;
    private String accessType;
    private List<String> connectorTypeList;
    private String schedule;

    public PointsCreateUseCaseImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   PointsRepository pointsRepository,
                                   UsersRepository usersRepository,
                                   Context context,
                                   PointsCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.pointsRepository = pointsRepository;
        this.usersRepository = usersRepository;
        this.context = context;
        this.callback = callback;
    }
    @Override
    public void setPointParameters(double lat, double lon, String town,
                                   String street, String number, String accessType,
                                   List<String> connectorTypeList, String schedule){
        this.lat = lat;
        this.lon = lon;
        this.town = town;
        this.street = street;
        this.number = number;
        this.accessType = accessType;
        this.connectorTypeList = connectorTypeList;
        this.schedule =  schedule;
    }
    @Override
    public void run() {
        final Point point = PointFactory.getInstance().createNewPoint(lat,lon,town,street,number,accessType,connectorTypeList,schedule);
        pointsRepository.createPoint(point, new PointsRepository.CreatePointCallback(){
            @Override
            public void onPointCreated(String id) {
                point.id = id;
                addPointToCurrentUser(point);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void addPointToCurrentUser(final Point point) {
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(context, new GetCurrentUserUseCase.Callback() {
            @Override
            public void onCurrentUserRetrieved(User currentUser) {
                currentUser.addPoint(point);

                usersRepository.saveUser(currentUser, new UsersRepository.SaveUserCallback() {
                    @Override
                    public void onUserSaved() {
                        postPoint(point.getId());
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        getCurrentUserUseCase.execute();

    }

    private void postPoint(final String id) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onPointCreated(id);
            }
        });
    }
}
