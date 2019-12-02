package com.example.catchtime.bean;

public class DetialBean {
    private int detailId;
    private int activityId;
    private int locationId;
    private String beginTime;
    private String finishTime;

    @Override
    public String toString() {
        return "DetialBean{" +
                "detailId=" + detailId +
                ", activityId=" + activityId +
                ", locationId=" + locationId +
                ", beginTime='" + beginTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                '}';
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public DetialBean(int detailId, int activityId, int locationId, String beginTime, String finishTime) {
        this.detailId = detailId;
        this.activityId = activityId;
        this.locationId = locationId;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
    }

    public DetialBean() {
    }
}
