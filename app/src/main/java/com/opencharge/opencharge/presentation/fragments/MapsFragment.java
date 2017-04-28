package com.opencharge.opencharge.presentation.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.opencharge.opencharge.R;

import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.helpers.MapSearchFeature;
import com.opencharge.opencharge.domain.helpers.impl.MapSearchFeatureImpl;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;
import com.opencharge.opencharge.presentation.locators.ServicesLocator;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Victor on 28/03/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentLocation;
    private UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
    private ServicesLocator servicesLocator = ServicesLocator.getInstance();
    private MarkerOptions mySearch = new MarkerOptions();
    private Marker myMarker;

    static final LatLng BARCELONA = new LatLng(41.390, 2.154);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP) {
            mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        } else {
            mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        }
        mapFragment.getMapAsync(this);

        getUserLocation();

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserLocation();
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14)); //40.000 km / 2^n, n=14
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
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
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            addMarkers();
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getActivity().getLayoutInflater().inflate(R.layout.content_tooltip, null);

                TextView access = (TextView) view.findViewById(R.id.access);
                TextView address = (TextView) view.findViewById(R.id.adreca);
                ImageView image = (ImageView) view.findViewById(R.id.image);

                access.setText(" " + marker.getTitle());
                address.setText(marker.getSnippet());
                int drawable = Point.getDrawableForAccess(marker.getTitle());
                image.setImageDrawable(getResources().getDrawable(drawable));

                return view;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Point point = (Point)marker.getTag();
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
                PointInfoFragment fragment = PointInfoFragment.newInstance(point.getId());
                ft.replace(R.id.content_frame, fragment).commit();
            }
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.navigation, menu);
        final MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LatLng searchLocation = searchInMap(query);
                if (searchLocation != null) {
                    if (myMarker != null) myMarker.remove();
                    mySearch.position(searchLocation);
                    mySearch.title(query);
                    mySearch.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    myMarker = mMap.addMarker(mySearch);
                    myMarker.hideInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchLocation, 10)); //40.000 km / 2^n, n=15
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                }
                else Toast.makeText(getActivity(),"Addreça invalida!",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        // When the action view is collapsed, reset the query
                        myMarker.remove();
                        // Return true to allow the action view to collapse
                        return true;
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
        markerOptions.title(point.getAccessType());
        markerOptions.snippet(point.getAddress());

        BitmapDescriptor icon;
        switch(point.getAccessType()) {
            case Point.PUBLIC_ACCESS:
                icon =  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                break;
            case Point.PRIVATE_ACCESS:
                icon =  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            case Point.PARTICULAR_ACCESS:
                icon =  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                break;
            default: icon =  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW); break;
        }
        markerOptions.icon(icon);

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


    private LatLng searchInMap(String name) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        MapSearchFeature MapSearchFeature = servicesLocator.getMapSearchFeature(geocoder);
        return MapSearchFeature.searchInMap(name);
    }

}
