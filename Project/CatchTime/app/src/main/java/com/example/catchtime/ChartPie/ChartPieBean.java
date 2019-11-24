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
    public int img;

    public ChartPieBean() {
    }

    public ChartPieBean(float value, String type, int colorRes,int img) {
        this.value = value;
        this.type = type;
        this.colorRes = colorRes;
        this.img=img;
    }
}