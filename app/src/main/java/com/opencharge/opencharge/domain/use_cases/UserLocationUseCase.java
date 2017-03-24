package com.opencharge.opencharge.domain.use_cases;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Oriol on 23/3/2017.
 */

public interface UserLocationUseCase extends ComponentCallbacks2, LocationListener {
    interface Callback {
        Location getLocation();
        void onPointsRetrieved(String message);
    }

    //Close the GPS
    void closeGPS();

    double getLatitude();

    double getLongitude();

    boolean canGetLocation();

    void showSettingsAlert();

    IBinder onBind(Intent arg0);

    void onLocationChanged(Location location);

    void onProviderDisabled(String provider);

    void onProviderEnabled(String provider);

    void onStatusChanged(String provider, int status, Bundle extras);
}
