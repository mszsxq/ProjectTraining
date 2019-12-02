package com.example.catchtime.bean;

public class ActivityBean {
    private int activityId;
    private String activityName;
    private int iconId;

    @Override
    public String toString() {
        return "ActivityBean{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", iconId=" + iconId +
                '}';
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public ActivityBean(int activityId, String activityName, int iconId) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.iconId = iconId;
    }

    public ActivityBean() {
    }
}
