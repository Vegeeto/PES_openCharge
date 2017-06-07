package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Oriol on 6/6/2017.
 */

public interface DeletePointUseCase extends UseCase {

    interface Callback {
        void onPointDeleted();
    }

    void setPointId(String point_id);

}
