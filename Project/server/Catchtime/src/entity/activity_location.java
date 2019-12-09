package entity;

public class activity_location {
    private double lat;
    private double lng;
    private String location_name;
    private String activity_name;
    private String startTime;
    private String endTime;
    private String detailAdd;
    private String icon;
    private String color;
    public activity_location(){}

    public activity_location(double lat, double lng, String location_name, String activity_name, String startTime, String endTime, String detailAdd, String icon, String color) {
        this.lat = lat;
        this.lng = lng;
        this.location_name = location_name;
        this.activity_name = activity_name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.detailAdd = detailAdd;
        this.icon = icon;
        this.color = color;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDetailAdd() {
        return detailAdd;
    }

    public String getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }
}