package com.opencharge.opencharge.presentation.fragments;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 28/03/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentLocation;
    private UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();

    static final LatLng BARCELONA = new LatLng(41.390, 2.154);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserLocation();

        FloatingActionButton update = (FloatingActionButton) view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserLocation();
            }
        });

        // Exemple de com cridar al use case per agafar el llistat de punts!
        //  1. Primer es fa una instancia del UseCase. T̩ un parametre que es un callback, una funcio que es cridar�� un cop
        //      el UseCase acabi de fer el que ha de fer (cridar al firebase en aquest cas)
        PointsListUseCase pointsListUseCase = useCasesLocator.getPointsListUseCase(new PointsListUseCase.Callback() {
            @Override
            public void onPointsRetrieved(String points) {
                //  3. Aqui es reben els punts, i es fa el que sigui, s'envien a la api de google maps per mostrar els punts, etc
            }
        });
        //  2. S'ha de cridar el execute per executar el use case, si no no fa res. En quan fas el execute es posa a fer el que sigui
        pointsListUseCase.execute();

    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();
        getUserLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
        if (currentLocation != null) {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14)); //40.000 km / 2^n, n=14

            //Test
            addMarkers();
        }
    }

    public void addMarkers() {
        List<LatLng> markers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LatLng l = new LatLng(BARCELONA.latitude+i, BARCELONA.longitude+i);
            markers.add(l);
        }
        int count = 0;
        for (LatLng l : markers) {
            mMap.addMarker(new MarkerOptions().position(l).title("Marker " + count));
            count++;
        }
    }


    public void getUserLocation() {
        UserLocationUseCase.Callback userLocationCallback = new UserLocationUseCase.Callback() {
            @Override
            public void onLocationRetrieved(Location location) {
                double latitude, longitude;
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    latitude = BARCELONA.latitude;
                    longitude = BARCELONA.longitude;
                }

                Log.i("Latitude: ", String.format("latitude: %s", latitude));
                Log.i("Location: ", String.format("longitude: %s", longitude));
                currentLocation = new LatLng(latitude, longitude);

                if (mMap != null) {
                    onMapReady(mMap);
                }
            }

            @Override
            public void onCanNotGetLocationError() {
                Log.e("MapsActivity", "TODO: implenetar alguna cosa quan no es pot agafar la localitzacio");
            }
        };

        UserLocationUseCase userLocationUseCase = useCasesLocator.getUserLocationUserCase(getActivity(), userLocationCallback);
        userLocationUseCase.execute();
    }

}
