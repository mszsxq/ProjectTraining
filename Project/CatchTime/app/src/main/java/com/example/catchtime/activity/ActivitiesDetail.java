package com.example.catchtime.activity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.catchtime.R;
import com.example.catchtime.chart.InitBarChart;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.Arrays;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class ActivitiesDetail extends SwipeBackActivity implements ObservableScrollViewCallbacks {

    private ObservableScrollView mScrollView;
    private ProgressBar progesss;
    private TextView min;
    private  ImageView back;
    private LinearLayout linearLayout ;
    private LineView lineView;
    private LineView lineView2;
    private View   barCharts;
    private LinearLayout l1;
    private LinearLayout l2;
    private LinearLayout l3;
    private int color;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_detail);
        //why 不能放在上面
        String colorstring= getIntent().getStringExtra("colortype");
        color =getResources().getIdentifier(colorstring, "color", getPackageName());
        getView();
        setListener();
        setColor();
        progesss.setProgress(20);
        //进度条
//        setPosWay1();
        min.setText(new StringBuffer().append(progesss.getProgress()).append("min"));


        //柱状图
        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i=0;i<10;i++){
            float multi =(100+1);
            float val = (float)(Math.random()*multi)+multi/3;
//            values.add(new BarEntry(i,val,getResources().getDrawable()));
            values.add(new BarEntry(i,val));
        }
//

        //折线图
        lineView.setData(
                Arrays.asList("02-1","02-1","02-1","02-1","02-1","02-1","02-1"),
                Arrays.asList(0.0f,1.0f,2.0f,1.0f,4.0f,5.0f,6.0f));
        lineView2.setData(
                Arrays.asList("02-1","02-1","02-1","02-1","02-1","02-1","02-1"),
                Arrays.asList(0.0f,1.0f,2.0f,1.0f,4.0f,5.0f,6.0f));
    }

    public void getView() {
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        l1= (LinearLayout) findViewById(R.id.view_weekoccupy);
        l2=(LinearLayout)findViewById(R.id.view_lastoccupy);
        l3=(LinearLayout)findViewById(R.id.view_totaloccupy);
        lineView2 =(LineView)findViewById(R.id.lineView2);
        barCharts= (BarChart)findViewById(R.id.barchar);
        lineView=(LineView) findViewById(R.id.lineView);
        back = (ImageView) findViewById(R.id.acdetails_back);
        progesss = (ProgressBar) findViewById(R.id.progesss1);
        min = (TextView) findViewById(R.id.min1);
        linearLayout = (LinearLayout) findViewById(R.id.topbar);
        new InitBarChart((BarChart) barCharts,null,this);
    }
    public void setListener(){
        back.setOnClickListener(new MyListener());
    }
    public void setColor(){
        linearLayout.setBackgroundColor(color);
        l1.setBackgroundColor(color);
        l2.setBackgroundColor(color);
        l3.setBackgroundColor(color);

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
<<<<<<< HEAD
=======

>>>>>>> 4e397161b2ed23d378bc3fc498e138eec2891bff
        if(linearLayout==null){
            Log.e("liner","null");
            return ;
        }
        if(scrollState == ScrollState.UP){

        }else if(scrollState == ScrollState.UP){

        }
    }
//    //进度条
//    private void setPosWay1() {
//       min.post(new Runnable() {
//            @Override
//            public void run() {
//                setPos();
//            }
//        });
//    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            setPos();
//        }
//    }
//    /**
//     * 设置进度显示在对应的位置
//     */
//    public void setPos() {
//        int w = getWindowManager().getDefaultDisplay().getWidth();
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) min.getLayoutParams();
//        int pro = progesss.getProgress();
//        int tW = min.getWidth();
//        if (w * pro / 100 + tW * 0.3 > w) {
//            params.leftMargin = (int) (w - tW * 1.1);
//        } else if (w * pro / 100 < tW * 0.7) {
//            params.leftMargin = 0;
//        } else {
//            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
//        }
//        min.setLayoutParams(params);
//
//    }
    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.acdetails_back :
                    finish();
                    break;

            }
        }
    }
}

