package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by ferran on 21/4/17.
 */

public interface PointByIdUseCase extends UseCase {
    interface Callback {
        void onPointRetrieved(Point point);
    }

    // TODO: Add usecase methods here
    public void setPointId(String pointId);
}
