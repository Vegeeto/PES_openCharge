package com.opencharge.opencharge.domain.use_cases;

import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Crjs on 02/06/2017.
 */

public interface ReservesUpdateConfirmationsUseCase extends UseCase {

    void setReserve(Reserve reserve);

}
