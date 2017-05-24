package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

import java.util.List;

/**
 * Created by Crjs on 16/04/2017.
 */

public interface PointsCreateUseCase extends UseCase {
    interface Callback {
        void onPointCreated(String id);
    }

    void setPointParameters(double lat, double lon, String town,
                                   String street, String number, String accessType,
                                   List<String> connectorTypeList, String schedule);
}
