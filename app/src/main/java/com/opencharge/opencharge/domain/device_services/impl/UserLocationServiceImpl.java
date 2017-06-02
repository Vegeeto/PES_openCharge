package com.opencharge.opencharge.domain.device_services.impl;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.app.Service;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.device_services.UserLocationService;

/**
 * Created by ferran on 24/3/17.
 */

public class UserLocationServiceImpl extends Service implements UserLocationService, LocationListener {

    /**
     *         Gps location tracker class
     *         to get users location and other information related to location
     */

    private UserLocationService.Callback callback;
    private Context context;
    private boolean isGpsEnabled = false;

    private Location userLocation;

    private static final long MIN_DISTANCE_FOR_UPDATE = 50; //Distance to update user location (in meters)

    private static final long MIN_TIME_FOR_UPDATE = 10000; //Time to update user location (in ms) [10 sec]

    private LocationManager mLocationManager;


    public UserLocationServiceImpl(Context context) {
        this.context = context;
        getLocation();
    }

    @Override
    public void getUserLocation(UserLocationService.Callback callback) {
        this.callback = callback;
        this.callback.onLocationRetrieved(getLocation());
    }


    private Location getLocation() {
        try {
            mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);            //Check if GPS is enabled
            //isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);    //Check if Network is enabled
            Log.d("TAG: ", "Hola");
            if (!isGpsEnabled) {
                showSettingsAlert();
            }

            //Check permissions
            if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                //Log.e("Error: ", "You don't have the right permissions to get User Location ");
                callback.onCanNotGetLocationError();
            } else {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                if (isGpsEnabled && mLocationManager != null) {             //Get the user location using gps
                    //Log.e("LOCATION: ", "Getting user locations using GPS");
                    userLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                } else { //Get the user location using network.
                    //Log.e("LOCATION: ", "Getting user locations using NETWORK");
                    userLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLocation;
    }


    private void showSettingsAlert() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));
        dialog.setTitle("GPS DESACTIVAT");
        dialog.setMessage("Voleu activar-lo?");

        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(mIntent);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog createDialog = dialog.create();
        createDialog.show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}
