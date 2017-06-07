package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.PointsRepository;
import com.opencharge.opencharge.domain.use_cases.DeletePointUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

/**
 * Created by Oriol on 6/6/2017.
 */

public class DeletePointUseCaseImpl extends AbstractUseCase implements DeletePointUseCase {

    private String pointId;
    private DeletePointUseCase.Callback callback;
    private PointsRepository pointsRepository;

    public DeletePointUseCaseImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  PointsRepository pointsRepository,
                                  DeletePointUseCase.Callback callback) {
        super(threadExecutor, mainThread);
        this.pointsRepository = pointsRepository;
        this.callback = callback;
    }

    @Override
    public void setPointId(String pointId){
        this.pointId = pointId;
    }

    @Override
    public void run() {
        pointsRepository.deletePoint(this.pointId, new PointsRepository.DeletePointCallback() {
            @Override
            public void onPointDeleted() {
                FirebaseDatabase.getInstance().getReference("Points").child(pointId).removeValue();
            }

            @Override
            public void onError() {
                Log.e("OnPointDeleted: ", "Error");
            }
        });
    }

}
