package com.opencharge.opencharge.domain.device_services;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Oriol on 25/4/2017.
 */

public interface AddressConversion {

    LatLng AddressToLatLng(String town, String street, String number);

    boolean LatLongToAddress(double lat, double lng);

    String getTown();

    String getStreet();

    String getNumber();
}
