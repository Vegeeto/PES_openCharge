package com.opencharge.opencharge.domain.helpers.impl;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.helpers.AddressConversion;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oriol on 25/4/2017.
 */

public class AddressConversionImpl implements AddressConversion {

    private String town;
    private String street;
    private String number;
    private Geocoder geocoder;

    public AddressConversionImpl(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public LatLng AddressToLatLng(String town, String street, String number) {
        try {
            String name = street + " " + number + ", " + town;
            List<Address> address = geocoder.getFromLocationName(name, 1);
            if (address != null && !address.isEmpty()) {
                return new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String LatLongToAddress(double lat, double lng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean LatLongToParameters(double lat, double lng) {
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            this.street = addresses.get(0).getAddressLine(0);
            this.town = addresses.get(0).getLocality();
            this.number = addresses.get(0).getThoroughfare();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getTown() {
        return this.town;
    }

    @Override
    public String getStreet() {
        return this.street;
    }

    @Override
    public String getNumber() {
        return this.number;
    }

}
