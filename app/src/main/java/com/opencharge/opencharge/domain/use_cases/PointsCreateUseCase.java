package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Crjs on 16/04/2017.
 */

public interface PointsCreateUseCase extends UseCase {
    interface Callback {
        void onPointCreated(Point point);
    }
}
