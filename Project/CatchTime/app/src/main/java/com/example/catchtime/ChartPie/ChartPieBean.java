package com.example.catchtime.ChartPie;


/**
 * Created by izhaohu on 2017/12/27.
 */

public class ChartPieBean {

    public float value;
    public String type;
    public float rate;
    public int colorRes;
    public float startAngle;
    public float sweepAngle;
    public float Angle;
    public int img;

    public float getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public float getRate() {
        return rate;
    }

    public int getColorRes() {
        return colorRes;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public float getAngle() {
        return Angle;
    }

    public int getImg() {
        return img;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setColorRes(int colorRes) {
        this.colorRes = colorRes;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setAngle(float angle) {
        Angle = angle;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public ChartPieBean() {
    }

    public ChartPieBean(float value, String type, int colorRes,int img) {
        this.value = value;
        this.type = type;
        this.colorRes = colorRes;
        this.img=img;
    }
}