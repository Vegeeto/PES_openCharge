package com.opencharge.opencharge.domain.parsers.impl;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.Entities.Point;

import com.opencharge.opencharge.domain.Entities.Point;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oriol on 20/4/2017.
 */

public class MapSearchAddressTest {

    MapSearchAddressTest sut;

    //Collaborators
    String address;
    LatLng addressLocation;
    private Geocoder geocoder;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new MapSearchAddressTest();
    }

    private void setUpCollaborators() {
        address = "Avinguda L'Eramprunyà 4, Gavà";
    }

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    private LatLng SearchInMap(String name) {
        try {
            List<Address> address = geocoder.getFromLocationName(name, 1);
            if(address != null && !address.isEmpty()) {
                return new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LatLng(-1, -1);
    }

    //<editor-fold desc="Id tests">
    @Test
    public void test_searchAddress_returnCorrectLatLng() {
        //When
        addressLocation = sut.SearchInMap(address);
        LatLng expectedResult = new LatLng(41.309447, 1.999968);
        //Then
        assertEquals("Address is correct", expectedResult, addressLocation);
    }
    //</editor-fold>

    //<editor-fold desc="Id tests">
    @Test
    public void test_searchAddress_noAddressFound() {
        //When
        addressLocation = sut.SearchInMap("unknown result");
        LatLng expectedResult = new LatLng(-1, -1);
        //Then
        assertEquals("Correct result", expectedResult, addressLocation);
    }
    //</editor-fold>

}