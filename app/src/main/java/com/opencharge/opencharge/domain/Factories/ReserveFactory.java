package com.opencharge.opencharge.domain.Factories;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;

import java.util.Date;

/**
 * Created by ferran on 5/6/17.
 */

public class ReserveFactory {
    private static ReserveFactory instance;

    private ReserveFactory() {
    }

    public static ReserveFactory getInstance() {
        if (instance == null) {
            instance = new ReserveFactory();
        }
        return instance;
    }

    public Reserve createNewReserve(Date day,
                                    Date startHour,
                                    Date endHour,
                                    Point point,
                                    String consumerUserId) {
        Reserve r = new Reserve(day, startHour, endHour);
        r.setPointId(point.getId());
        r.setConsumerUserId(consumerUserId);
        r.setSupplierUserId(point.userId);

        return r;
    }
}
