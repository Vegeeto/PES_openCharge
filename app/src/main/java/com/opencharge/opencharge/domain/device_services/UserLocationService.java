package com.opencharge.opencharge.domain.device_services;

import android.location.Location;

/**
 * Created by ferran on 24/3/17.
 */

public interface UserLocationService {
    interface Callback {
        void onLocationRetrieved(Location location);
        void onCanNotGetLocationError();
    }

    void getUserLocation(UserLocationService.Callback callback);
}
