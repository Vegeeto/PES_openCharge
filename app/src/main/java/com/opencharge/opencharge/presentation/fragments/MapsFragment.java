package com.opencharge.opencharge.presentation.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencharge.opencharge.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends MyFragment implements OnMapReadyCallback {

    private GoogleMap map;
    static final LatLng BARCELONA = new LatLng(41.390, 2.154);
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Boolean flag = false;

    //Default empty constructor
    public MapsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) fActivity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) fActivity.getSystemService(LOCATION_SERVICE);

        flag = displayGpsStatus();
        if (flag) {
            locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    Toast.makeText(fActivity, "Location changed : Lat: " +
                                    location.getLatitude() + " Lng: " + location.getLongitude(),
                            Toast.LENGTH_SHORT).show();
                    String longitude = "Longitude: " + location.getLongitude();
                    Log.v("Longitud: ", longitude);
                    String latitude = "Latitude: " + location.getLatitude();
                    Log.v("Latitud: ", latitude);

                    String s = longitude + "\n" + latitude + "\n";
                    Log.v("Current city: ", s);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            if (ActivityCompat.checkSelfPermission(fActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e("Permissos: ", "Falten permisos");
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                locationManager.removeUpdates(locationListener);
            }
        } else {
            Log.e("Gps Status!!", "Your GPS is: OFF");
        }

        return v;
    }


    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {

        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_COARSE);
        locationCritera.setAltitudeRequired(false);
        locationCritera.setBearingRequired(false);
        locationCritera.setCostAllowed(true);
        locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String providerName = locationManager.getBestProvider(locationCritera, true);

        if (providerName != null && locationManager.isProviderEnabled(providerName)) {
            // Provider is enabled
            return true;
        } else {
            // Provider not enabled, prompt user to enable it
            Toast.makeText(fActivity, R.string.turn_on_gps, Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            MapsFragment.this.startActivity(myIntent);
            return false;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng barcelona = BARCELONA;
        map.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona"));
        map.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
    }

}
