package com.example.catchtime.chart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.catchtime.Add_detailPage;
import com.example.catchtime.ModifyPage;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.notimportant.DemoBase;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class InitPieChart  extends DemoBase implements OnChartValueSelectedListener {
    private ImageButton add;
    private ImageButton change;
    private ImageButton data;
    private RelativeLayout linearLayout;
    private View fragment;
    private int flag;//表示显示的是第几个位置
    private PieChart chart;
    private ArrayList<PieEntry> entries;
    private Context context;
    private String centerString;
    private int flag1;//用来表示年月周日
    public void setCenterString(String centerString){
        this.centerString=centerString;
    }
    public InitPieChart(PieChart chart, ArrayList<PieEntry> entries, Context context
                    ,ImageButton add,ImageButton change,ImageButton data,RelativeLayout linearLayout,int flag,int flag1){
        this.chart=chart;
        this.entries=entries;
        this.context=context;
        this.flag=flag;
        this.add=add;
        this.change=change;
        this.data=data;
        this.linearLayout=linearLayout;
        this.flag1=flag1;
        if (entries==null||entries.size()==0){
            for (int i = 0; i < 5 ; i++) {
                PieEntry pieEntry=new PieEntry((float) ((Math.random() * 5) + 5 / 5),"测试",getResources().getDrawable(R.drawable.marker2));
                entries.add(pieEntry);
            }
        }
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);//设置中间空白圆是否显示
        chart.setHoleColor(Color.WHITE);//中间空白圆的颜色

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(50);

        chart.setHoleRadius(50f);//设置中间圆的大小
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);//设置显示中间字体

        chart.setRotationAngle(5);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);//设置每个扇形可以显示

//         chart.setUnit(" €");
//         chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);
//         chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);

        setData(entries);//设置数据函数

    }


    private void setData(ArrayList<PieEntry> entries) {

        PieDataSet dataSet = new PieDataSet(entries, "");//设置显示的扇形列表
        dataSet.setDrawIcons(true);//设置是否显示图标
        dataSet.setSliceSpace(0);
        dataSet.setIconsOffset(new MPPointF(0, 0));//设置图标显示的位置
        dataSet.setSelectionShift(5f);//设置点击时突出的部分大小

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }



    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "PieChartActivity");
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s;
        if (centerString==null){
             s = new SpannableString("life\ntimer");
        }else {
             s = new SpannableString(centerString);
        }
        return s;
    }


    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
        if (flag1==0){
            linearLayout.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("按钮","点击了第"+flag+"天的选中");
                    Intent intent=new Intent(context, ModifyPage.class);
                    context.startActivity(intent);
                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("按钮","点击了第"+flag+"天的改变");
                    Intent intent=new Intent(context, Add_detailPage.class);
                    context.startActivity(intent);
                }
            });
            data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("按钮","点击了第"+flag+"天的数据");
                    Intent intent=new Intent(context, ActivitiesDetail.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {}


    public void onStopTrackingTouch(SeekBar seekBar) {}
}
