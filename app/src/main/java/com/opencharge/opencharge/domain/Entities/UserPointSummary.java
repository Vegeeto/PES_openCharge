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
}
