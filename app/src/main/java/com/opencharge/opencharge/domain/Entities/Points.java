package com.opencharge.opencharge.domain.Entities;

/**
 * Created by Crjs on 24/03/2017.
 */

public class Points {
    //xCoord and yCoord string
    private float xCoord;
    private float yCoord;

    public Points() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public float getxCoord() {
        return xCoord;
    }

    public void setxCoord(float xCoord) {
        this.xCoord = xCoord;
    }

    public float getyCoord() {
        return yCoord;
    }

    public void setyCoord(float yCoord) {
        this.yCoord = yCoord;
    }
}
