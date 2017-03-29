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
import com.opencharge.opencharge.domain.use_cases.UserLocationUseCase;

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
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;

    private Location userLocation;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10; //Distance to update user location

    private static final long MIN_TIME_FOR_UPDATE = 60000; //Time to update user location (1 min)

    private LocationManager mLocationManager;


    public UserLocationServiceImpl(Context context) {
        this.context = context;
        this.canGetLocation = false;
        getLocation();
    }

    @Override
    public void getUserLocation(UserLocationService.Callback callback) {
        this.callback = callback;
        Location location = getLocation();
        this.callback.onLocationRetrieved(location);
    }


    private Location getLocation() {
        try {
            mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);            //Check if GPS is enabled
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);    //Check if Network is enabled
            //System.out.println("GPS enabled: " + isGpsEnabled);
            //System.out.println("NETWORK enabled: " + isNetworkEnabled);
            if (!isGpsEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                //Check permissions
                if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                    Log.e("Error: ", "You don't have the right permissions to get User Location ");
                    callback.onCanNotGetLocationError();
                } else {

                    this.canGetLocation = true;
                    if (canGetLocation && isGpsEnabled) {             //Get the user location using gps
                        //System.out.println("Getting user locations using GPS");
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                        if (mLocationManager != null) {
                            userLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }

                    } else if (canGetLocation && isNetworkEnabled) {  //Get the user location using network.
                        //System.out.println("Getting user locations using NETWORK");
                        //mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE__FOR_UPDATE, this);
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, this);
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, this);
                        if (mLocationManager != null) {
                            userLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLocation;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    private void showSettingsAlert() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));
        dialog.setTitle("GPS DESACTIVAT");
        dialog.setMessage("Voleu activar el GPS?");

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
