package com.example.catchtime.chart;

import android.content.Context;
import android.widget.SeekBar;

import com.example.catchtime.notimportant.DemoBase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class InitBarChart extends DemoBase {
    private BarChart chart;
    private ArrayList<BarEntry> entries;
    private Context context;


    public InitBarChart(BarChart chart, ArrayList<BarEntry> entries, Context context) {
        this.chart = chart;
        this.entries = entries;
        this.context = context;


        chart.getDescription().setEnabled(true);

        chart.setMaxVisibleValueCount(60);//设置最大显示列数

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setDrawBarShadow(false);//设置条形图的音影是否显示
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);//设置是否显示x轴的线


        chart.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);

        onSetData(entries,true);
    }

    public void onSetData(ArrayList<BarEntry> values, boolean fromUser) {



        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(true);//设置条形框上显示大小

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setFitBars(true);
        }

        chart.invalidate();
    }



    protected void saveToGallery() {
        saveToGallery(chart, "AnotherBarActivity");
    }


    public void onStartTrackingTouch(SeekBar seekBar) {}


    public void onStopTrackingTouch(SeekBar seekBar) {}
}
