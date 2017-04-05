package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Points;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by ferran on 15/3/17.
 */

public interface PointsListUseCase extends UseCase {
    interface Callback {
        void onPointsRetrieved(Points[] points);
    }

    // TODO: Add usecase methods here
}
