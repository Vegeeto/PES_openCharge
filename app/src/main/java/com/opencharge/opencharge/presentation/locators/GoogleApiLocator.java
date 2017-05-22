package com.opencharge.opencharge.presentation.locators;

/**
 * Created by Oriol on 21/5/2017.
 */

import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleApiLocator {

    private static final String TAG = "MyGoogleApiClient_Singleton";
    private static GoogleApiLocator instance = null;

    private static GoogleApiClient mGoogleApiClient = null;

    protected GoogleApiLocator() {
    }

    public static GoogleApiLocator getInstance(GoogleApiClient aGoogleApiClient) {
        if(instance == null) {
            instance = new GoogleApiLocator();
            if (mGoogleApiClient == null)
                mGoogleApiClient = aGoogleApiClient;
        }
        return instance;
    }

    public GoogleApiClient getGoogleApiClient(){
        return mGoogleApiClient;
    }
}
