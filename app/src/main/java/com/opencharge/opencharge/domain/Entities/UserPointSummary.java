package com.opencharge.opencharge.domain.Entities;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ferran on 3/6/17.
 */

public class UserPointSummary {
    public static final String UNKNOWN_ACCESS = "Desconegut";
    public static final String PUBLIC_ACCESS = "Public";
    public static final String PRIVATE_ACCESS = "Privat";
    public static final String PARTICULAR_ACCESS = "Particular";

    @StringDef({UNKNOWN_ACCESS, PUBLIC_ACCESS, PRIVATE_ACCESS, PARTICULAR_ACCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessType {}

    public @Point.AccessType String accessType;

    private String pointId;
    private String pointAddress;


    public UserPointSummary(String pointId, String pointAddress, @Point.AccessType String accessType) {
        this.pointId = pointId;
        this.pointAddress = pointAddress;
        this.accessType = accessType;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointAddress() {
        return pointAddress;
    }

    public void setPointAddress(String pointAddress) {
        this.pointAddress = pointAddress;
    }

    public @Point.AccessType    String getAccessType() { return accessType; }

    public void setAccessType(@Point.AccessType String accessType) { this.accessType = accessType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPointSummary that = (UserPointSummary) o;

        if (pointId != null ? !pointId.equals(that.pointId) : that.pointId != null) return false;
        return pointAddress != null ? pointAddress.equals(that.pointAddress) : that.pointAddress == null;

    }

    @Override
    public int hashCode() {
        int result = pointId != null ? pointId.hashCode() : 0;
        result = 31 * result + (pointAddress != null ? pointAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserPointSummary{" +
                "pointId='" + pointId + '\'' +
                ", pointAddress='" + pointAddress + '\'' +
                '}';
    }
}
