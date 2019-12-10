package com.example.catchtime.entity;

public class Activity {
    private int activity_id;
    private int icon_id;
    private String icon_name;
    private String icon_color;
    private String activity_name;
    private int image;
    private int color;


    public Activity() {
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public String getIcon_color() {
        return icon_color;
    }

    public void setIcon_color(String icon_color) {
        this.icon_color = icon_color;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

}
