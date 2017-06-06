package com.opencharge.opencharge.domain.device_services.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.opencharge.opencharge.domain.device_services.UserPreferences;

import java.util.Objects;

/**
 * Created by ferran on 3/6/17.
 */

public class UserPreferenceImpl implements UserPreferences {
    private SharedPreferences preferences;

    private static final String CURRENT_USER_KEY = "curentUser";
    private static final String NO_CURRENT_USER_VALUE = "NO User";

    public UserPreferenceImpl(Context context) {
        preferences = context.getSharedPreferences("OpenChargeApp", Context.MODE_PRIVATE);
    }

    @Override
    public void setCurrentUserValue(String currentUser) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_USER_KEY, currentUser);
        editor.commit();
    }

    @Override
    public String getCurrentUserValue() {
        String currentUser = preferences.getString(CURRENT_USER_KEY, NO_CURRENT_USER_VALUE);

        if (Objects.equals(currentUser, NO_CURRENT_USER_VALUE)) {
            return null;
        }

        return currentUser;
    }
}
