package com.opencharge.opencharge.domain.parsers.impl;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.device_services.impl.MapSearchFeatureImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Oriol on 20/4/2017.
 */

public class MapSearchFeatureTest {

    MapSearchFeatureImpl sut;

    //Collaborators
    String[] address;
    LatLng addressLocation;
    @Mock
    private Geocoder geocoder;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new MapSearchFeatureImpl(geocoder);
    }

    private void setUpCollaborators() {
        address[0] = "Avinguda L'Eramprunyà 4, Gavà";
        address[1] = "unknown result";
    }

    //<editor-fold desc="Id tests">
    @Test
    public void testRight_searchAddress_returnLatLng() {
        //Given
        Address mockAddress = new Address(Locale.getDefault());
        mockAddress.setLatitude(41.309447);
        mockAddress.setLongitude(1.999968);
        List<Address> mock = new ArrayList<>(1);
        mock.add(mockAddress);
        try {
            when(geocoder.getFromLocationName(any(String.class), anyInt())).thenReturn(mock);
        } catch (IOException e) {

        }
        //When
        addressLocation = sut.searchInMap(address[0]);
        LatLng expectedResult = new LatLng(41.309447, 1.999968);
        //Then
        assertEquals("Address is correct", expectedResult, addressLocation);
    }
    //</editor-fold>

    //<editor-fold desc="Id tests">
    @Test
    public void testWrong_searchAddress_returnNull() {
        //When
        addressLocation = sut.searchInMap(address[1]);
        //Then
        assertNull("Correct result", addressLocation);
    }
    //</editor-fold>

}