package com.opencharge.opencharge.domain.device_services;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.domain.device_services.impl.MapSearchFeatureImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oriol on 20/4/2017.
 */

public class MapSearchFeatureTest {

    private MapSearchFeatureImpl sut;

    //Collaborators
    private List<Address> addressesGeneratedByGeocoder;
    private LatLng correctLatLng;

    @Mock
    private Geocoder geocoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUpCollaborators();
        sut = new MapSearchFeatureImpl(geocoder);
    }

    private void setUpCollaborators() {
        double lat = 41.309447;
        double lon = 1.999968;

        Address address = new Address(Locale.getDefault());
        address.setLatitude(lat);
        address.setLongitude(lon);

        addressesGeneratedByGeocoder = new ArrayList<>(1);
        addressesGeneratedByGeocoder.add(address);

        correctLatLng = new LatLng(lat, lon);
    }

    @Test
    public void testRightAddress_searchAddress_returnCorrectLatLng() throws IOException {
        //Given
        when(geocoder.getFromLocationName(any(String.class), anyInt())).thenReturn(addressesGeneratedByGeocoder);

        //When
        LatLng returnedLatLng = sut.searchInMap("Some Correct Address");

        //Then
        assertEquals("Returned LatLng is not correct", correctLatLng, returnedLatLng);
    }

    @Test
    public void testWrongAddress_searchAddress_returnNull() throws IOException {
        //Given
        when(geocoder.getFromLocationName(any(String.class), anyInt())).thenReturn(null);

        //When
        LatLng returnedLatLng = sut.searchInMap("Some Wrong Address");

        //Then
        assertNull("Null not returned", returnedLatLng);
    }

}