package com.example.catchtime.bean;

public class LocationBean {
    private int locationId;
    private String locationName;
    private double locationLat;
    private double locationLng;
    private int locationRange;
    private String locationDetial;

    public LocationBean() {
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", locationLat=" + locationLat +
                ", locationLng=" + locationLng +
                ", locationRange=" + locationRange +
                ", locationDetial='" + locationDetial + '\'' +
                '}';
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public void setLocationRange(int locationRange) {
        this.locationRange = locationRange;
    }

    public void setLocationDetial(String locationDetial) {
        this.locationDetial = locationDetial;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public int getLocationRange() {
        return locationRange;
    }

    public String getLocationDetial() {
        return locationDetial;
    }

    public LocationBean(int locationId, String locationName, double locationLat, double locationLng, int locationRange, String locationDetial) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.locationRange = locationRange;
        this.locationDetial = locationDetial;
    }
}
