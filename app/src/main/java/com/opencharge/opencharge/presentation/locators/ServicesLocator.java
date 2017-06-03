package com.opencharge.opencharge.presentation.locators;

import android.content.Context;
import android.location.Geocoder;

import com.opencharge.opencharge.domain.device_services.UserPreferences;
import com.opencharge.opencharge.domain.device_services.impl.UserPreferenceImpl;
import com.opencharge.opencharge.domain.helpers.MapSearchFeature;
import com.opencharge.opencharge.domain.device_services.UserLocationService;
import com.opencharge.opencharge.domain.helpers.impl.MapSearchFeatureImpl;
import com.opencharge.opencharge.domain.device_services.impl.UserLocationServiceImpl;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.executor.impl.ThreadExecutor;
import com.opencharge.opencharge.threading.MainThreadImpl;

/**
 * Created by ferran on 22/3/17.
 */

public class ServicesLocator {
    private static ServicesLocator instance;

    private ServicesLocator() {}

    public static ServicesLocator getInstance() {
        if(instance == null) {
            instance = new ServicesLocator();
        }
        return instance;
    }

    public MainThread getMainThread() {
        return MainThreadImpl.getInstance();
    }

    public Executor getExecutor() {
        return ThreadExecutor.getInstance();
    }

    public UserLocationService getUserLocationService(Context context) {
        return new UserLocationServiceImpl(context);
    }

    public MapSearchFeature getMapSearchFeature(Geocoder geocoder) {
        return new MapSearchFeatureImpl(geocoder);
    }

    public UserPreferences getUserPreferencesService(Context context) {
        return new UserPreferenceImpl(context);
    }
}
