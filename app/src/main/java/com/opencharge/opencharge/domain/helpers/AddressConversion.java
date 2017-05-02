package com.opencharge.opencharge.domain.helpers;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Oriol on 25/4/2017.
 */

public interface AddressConversion {

    LatLng AddressToLatLng(String town, String street, String number);

    boolean LatLongToParameters(double lat, double lng);

    String LatLongToAddress(double lat, double lng);

    String getTown();

    String getStreet();

    String getNumber();
}
