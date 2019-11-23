package com.example.catchtime;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import com.example.catchtime.ChartPie.ChartPie;
import com.example.catchtime.ChartPie.ChartPieBean;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<ChartPieBean> pieBeanList;
    private LinearLayout lineLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    protected void initView() {
        lineLayoutList = findViewById(R.id.line_layout_list);
        initData();
        drawPie();
    }
    private void initData() {
        pieBeanList = new ArrayList<>();
        pieBeanList.add(new ChartPieBean(3090, "运动", R.color.main_green,R.drawable.run));
        pieBeanList.add(new ChartPieBean(501f, "娱乐", R.color.blue_rgba_24_261_255,R.drawable.yule));
        pieBeanList.add(new ChartPieBean(800, "睡觉", R.color.orange,R.drawable.moon));
        pieBeanList.add(new ChartPieBean(1000, "学校", R.color.red_2,R.drawable.school));
        pieBeanList.add(new ChartPieBean(2300, "路上", R.color.progress_color_default,R.drawable.load));
    }

    private void drawPie() {
        //底部的曲线图
        View childAt = View.inflate(this, R.layout.item_chart_pie, null);
        lineLayoutList.addView(childAt);
        ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        chartPie.setData(pieBeanList).start();
        chartPie.start();
    }


}
