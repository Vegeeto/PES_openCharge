package com.opencharge.opencharge.domain.device_services.impl;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.device_services.MapSearchFeature;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
