package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.List;

/**
 * Created by ferran on 6/6/17.
 */

public interface PointsEditUseCase extends UseCase {
    interface Callback {
        void onPointEdited();
    }

    void setPoint(Point point);
    void setPointParameters(double lat, double lon, String town,
                            String street, String number, String accessType,
                            List<String> connectorTypeList, String schedule);
}
