package com.opencharge.opencharge.domain.helpers.impl;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.helpers.MapSearchFeature;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oriol on 21/4/2017.
 */

public class MapSearchFeatureImpl implements MapSearchFeature {

    private Geocoder geocoder;

    public MapSearchFeatureImpl(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public LatLng searchInMap(String name) {
        try {

            List<Address> address = geocoder.getFromLocationName(name, 1);
            if (address != null && !address.isEmpty()) {
                return new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
