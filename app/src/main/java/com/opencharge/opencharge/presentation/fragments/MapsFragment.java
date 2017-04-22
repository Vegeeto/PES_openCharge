package com.opencharge.opencharge.presentation.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.device_services.impl.MapSearchFeatureImpl;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserLocation();

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserLocation();
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14)); //40.000 km / 2^n, n=14
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean noPermissions = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (noPermissions) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("ERROR: ", "No permissions");
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14)); //40.000 km / 2^n, n=14
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            addMarkers();

            //Test: Successful!
            //searchInMap("Avinguda L'Eramprunyà 4, Gavà");
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getActivity(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Point point = (Point)marker.getTag();
                android.app.FragmentManager fm = getFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(point.getId());
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });
    }

    public void addMarkers() {
        PointsListUseCase pointsListUseCase = useCasesLocator.getPointsListUseCase(new PointsListUseCase.Callback() {
            @Override
            public void onPointsRetrieved(Point[] points) {
                for (Point point : points) {
                    addMarkerForPoint(point);
                }
            }

        });
        pointsListUseCase.execute();
    }

    private void addMarkerForPoint(Point point) {
        LatLng position = new LatLng(point.getLatCoord(), point.getLonCoord());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title("Punt de càrrega:");
        markerOptions.snippet("Accés: " + point.getAccessType() + "\n Direcció: " + point.getStreet());

        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(point);
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
                Log.e("MapsFragment: ", "TODO: implementar alguna cosa quan no es pot agafar la localitzacio");
            }
        };

        UserLocationUseCase userLocationUseCase = useCasesLocator.getUserLocationUseCase(getActivity(), userLocationCallback);
        userLocationUseCase.execute();
    }


    public LatLng searchInMap(String name) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        MapSearchFeatureImpl MapSearchFeature = new MapSearchFeatureImpl(geocoder);
        LatLng searchLocation = MapSearchFeature.searchInMap(name);
        if (searchLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchLocation, 10)); //40.000 km / 2^n, n=15
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }

        return searchLocation;
    }
}
