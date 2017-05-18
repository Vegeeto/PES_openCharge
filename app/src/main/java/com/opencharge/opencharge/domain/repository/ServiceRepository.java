package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.Service;

import java.util.Date;

/**
 * Created by Oriol on 12/5/2017.
 */

public interface ServiceRepository {

    interface GetServicesForPointAtDayCallback {
        void onServicesRetrieved(Service[] services);
        void onError();
    }

    interface CreateServiceCallback {
        void onServiceCreated();
        void onError();
    }

    void createService(String point_id, Service service, final CreateServiceCallback callback);
    void getServicesForPointAtDay(String point_id, Date day, final GetServicesForPointAtDayCallback callback);

}
