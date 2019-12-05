package com.example.catchtime.entity;

public class Location {
    private int locationId;
    private String locationName;
    private Double locationLat;
    private Double locationLng;
    private int locationRange;
    private String locationDetail;

    public int getLocationId() {
        return locationId;
    }
    public void setLocationId(int i) {
        this.locationId = i;
    }
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public Double getLocationLat() {
        return locationLat;
    }
    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }
    public Double getLocationLng() {
        return locationLng;
    }
    public void setLocationLng(Double locationLng) {
        this.locationLng = locationLng;

    }
    public int getLocationRange() {
        return locationRange;
    }
    public void setLocationRange(int locationRange) {
        this.locationRange = locationRange;
    }
    public String getLocationDetail() {
        return locationDetail;
    }
    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;

    }

}

