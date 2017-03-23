package com.opencharge.opencharge.presentation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.executor.UserLocation;
import com.opencharge.opencharge.domain.executor.impl.UserLocationImpl;
import com.opencharge.opencharge.domain.use_cases.PointsListUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UserLocation userLocation;
    private LatLng currentLocation;

    static final LatLng BARCELONA = new LatLng(41.390, 2.154);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userLocation = new UserLocationImpl(this);
        currentLocation = BARCELONA;
        System.out.println("Hola");
        System.out.println(userLocation.canGetLocation());
        if (userLocation.canGetLocation()) {
            System.out.println("Aqui entra");
            double latitude = userLocation.getLatitude();
            double longitude = userLocation.getLongitude();
            Log.i("Latitude: ", String.format("latitude: %s", latitude));
            Log.i("Location: ", String.format("longitude: %s", longitude));
            currentLocation = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        }


        /**
         * Exemple de com cridar al use case per agafar el llistat de punts!
         */

        //  1. Primer es fa una instancia del UseCase. Té un parametre que es un callback, una funcio que es cridarà un cop
        //      el UseCase acabi de fer el que ha de fer (cridar al firebase en aquest cas)
        PointsListUseCase pointsListUseCase = UseCasesLocator.getInstance().getPointsListUseCase(new PointsListUseCase.Callback() {
            @Override
            public void onPointsRetrieved(String points) {
                //  3. Aqui es reben els punts, i es fa el que sigui, s'envien a la api de google maps per mostrar els punts, etc
            }
        });
        //  2. S'ha de cridar el execute per executar el use case, si no no fa res. En quan fas el execute es posa a fer el que sigui
        pointsListUseCase.execute();
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
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10)); //15 com a molt
    }

}
