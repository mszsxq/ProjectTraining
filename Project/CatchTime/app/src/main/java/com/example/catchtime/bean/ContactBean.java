package com.example.catchtime.bean;

public class ContactBean {
    private int locationId;
    private int activiyId;

    @Override
    public String toString() {
        return "ContactBean{" +
                "locationId=" + locationId +
                ", activiyId=" + activiyId +
                '}';
    }

    public ContactBean() {
    }

    public ContactBean(int locationId, int activiyId) {
        this.locationId = locationId;
        this.activiyId = activiyId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getActiviyId() {
        return activiyId;
    }

    public void setActiviyId(int activiyId) {
        this.activiyId = activiyId;
    }
}
