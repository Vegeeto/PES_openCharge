package com.opencharge.opencharge.domain.device_services;

/**
 * Created by ferran on 3/6/17.
 */

public interface UserPreferences {
    void setCurrentUserValue(String currentUser);
    String getCurrentUserValue();
}
