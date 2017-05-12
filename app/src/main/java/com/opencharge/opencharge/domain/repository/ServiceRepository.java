package com.opencharge.opencharge.domain.repository;

import com.opencharge.opencharge.domain.Entities.FirebaseService;
import com.opencharge.opencharge.domain.Entities.Service;

/**
 * Created by Oriol on 12/5/2017.
 */

public interface ServiceRepository {

    interface GetServicesCallback {
        void onServicesRetrieved(Service[] services);

        void onError();
    }

    interface GetServiceByIdCallback {
        void onPointRetrieved(Service service);

        void onError();
    }

    interface CreateServiceCallback {
        void onServiceCreated(String id);

        void onError();
    }

    void createService(String point_id, FirebaseService service, final CreateServiceCallback callback);

    void getServices(String point_id, final GetServicesCallback callback);

}
