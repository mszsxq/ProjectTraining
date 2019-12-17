package com.example.catchtime.backgroundService;

import android.os.Bundle;

import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.github.mikephil.charting.charts.PieChart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimedPopup extends AppCompatActivity {
    private PieChart chart1;
    private PieChart chart2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timedpopup);
        chart1=findViewById(R.id.chart1);
        chart2=findViewById(R.id.chart2);

        new InitPieChart(chart1,null,this,null,null,null,null,0,0,null,false,0,0);
        new InitPieChart(chart2,null,this,null,null,null,null,0,0,null,false,0,0);

    }
}
