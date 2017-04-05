package com.opencharge.opencharge.domain.use_cases;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

import com.opencharge.opencharge.domain.use_cases.base.UseCase;

/**
 * Created by Oriol on 23/3/2017.
 */

public interface UserLocationUseCase extends UseCase {
    interface Callback {
        void onLocationRetrieved(Location location);
        void onCanNotGetLocationError();
    }
}
