package com.opencharge.opencharge.domain.Entities;

/**
 * Created by ferran on 3/6/17.
 */

public class UserPointSummary {
    private String pointId;
    private String pointAddress;

    public UserPointSummary(String pointId, String pointAddress) {
        this.pointId = pointId;
        this.pointAddress = pointAddress;
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
