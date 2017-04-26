package com.opencharge.opencharge.domain.device_services;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Oriol on 21/4/2017.
 */

public interface MapSearchFeature {

    LatLng searchInMap(String name);

}
