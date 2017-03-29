package com.opencharge.opencharge.presentation.locators;

import android.util.Log;

import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.impl.PointsListUseCaseImpl;

/**
 * Created by ferran on 22/3/17.
 */

public class UseCasesLocator {
    private static UseCasesLocator instance;

    private UseCasesLocator() {
    }

    public static UseCasesLocator getInstance() {
        if(instance == null) {
            Log.d("UseCasesLocator","Creating new instance");
            instance = new UseCasesLocator();
        }
        Log.d("UseCasesLocator","Instance already exists, returning");
        return instance;
    }

    public PointsListUseCase getPointsListUseCase(PointsListUseCase.Callback callback) {
        Log.d("UseCasesLocator","Im inside getPointUseCase");
        return new PointsListUseCaseImpl(
                ServicesLocator.getInstance().getExecutor(),
                ServicesLocator.getInstance().getMainThread(),
                RepositoriesLocator.getInstance().getPointsRepository(),
                callback
        );
    }


}
