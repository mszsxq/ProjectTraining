package com.example.catchtime.activity;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.catchtime.R;
import com.example.catchtime.chart.InitBarChart;
import com.example.catchtime.entity.All_data;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class ActivitiesDetail extends SwipeBackActivity implements ObservableScrollViewCallbacks {

    private ObservableScrollView mScrollView;
    private ProgressBar progessssun;
    private ProgressBar progesssmon;
    private ProgressBar progessstue;
    private ProgressBar progessswen;
    private ProgressBar progesssthu;
    private ProgressBar progesssfri;
    private ProgressBar progessssat;
    private TextView minsun;
    private TextView minmon;
    private TextView mintue;
    private TextView minwen;
    private TextView minthu;
    private TextView minfri;
    private TextView minsat;
    private  ImageView back;
    private LinearLayout linearLayout ;
    private LineView lineView;
    private LineView lineView2;
    private View   barCharts;
    private LinearLayout l1;
    private LinearLayout l2;
    private LinearLayout l3;
    private int color;
    private String ip="http://192.168.2.133:8080/Catchtime/ActivitiesDetailServlet?activity=cycle&id=1";
//    private String ip="http://192.168.43.232:8080/Catchtime/ActivitiesDetailServlet?activity=cycle&id=1";
//    private String ip="http://10.7.89.247:8080/Catchtime/ActivitiesDetailServlet?activity=cycle&id=1";

    private  static Handler handler;
    private ArrayList<All_data> list=null;
    private List<All_data> listm= new ArrayList<>();
    private List<All_data> listw = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_detail);
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String info = (String)msg.obj;
                Gson gson =new Gson();
                list =gson.fromJson(info,new TypeToken<List<All_data>>(){}.getType());
                showData();
                progressBarShow();
            }
        };
        getView();
        setListener();
        setColor();
<<<<<<< HEAD
        sendMessage();


    }
=======
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
>>>>>>> e2375be026e9d36c03a45e2c2edcefb351439b66

    private void showData() {
        listWeekly();
        listMonthly();
        ArrayList<String>  bottom =new ArrayList<>();
        ArrayList<String>  top =new ArrayList<>();
//        for (All_data a :listw){
//            String date =
//            bottom.add()
//        }
        lineView.setData(
                Arrays.asList("02-1","02-1","02-1","02-1","02-1","02-1","02-1"),
                Arrays.asList(0.0f,1.0f,2.0f,1.0f,4.0f,5.0f,6.0f));
        lineView2.setData(
                Arrays.asList("02-1","02-1","02-1","02-1","02-1","02-1","02-1"),
                Arrays.asList(0.0f,1.0f,2.0f,1.0f,4.0f,5.0f,6.0f));

        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i=0;i<listw.size();i++){
            values.add(new BarEntry(i+1, Float.parseFloat(listw.get(i).getActivity_data())));
        }
        new InitBarChart((BarChart) barCharts,values,this);
    }

    public void listMonthly(){
        for(int i=0;i<30;i++){
            if(list.get(i)==null){
                listm.add(0,new All_data());
            }else {
                if(i%3==0){
                    listm.add(list.get(i));
                }
            }

        }
    }
    public void listWeekly(){

        for(int i=0;i<7;i++){
            if (list.get(i) == null) {
                if (listw != null) {
                    listw.add(0,new All_data());
                }
            }else {
                if (listw != null) {
                    listw.add(list.get(i));
                }
            }
        }
    }
    public void progressBarShow(){
        for(All_data a:listw){
            switch (dateToWeek(a.getDate())){
                case "Sun":
                    progessssun.setProgress(Integer.parseInt(a.getActivity_data()));
                    minsun.setText(new StringBuffer().append(progessssun.getProgress()/60).append("h"));
                    break;
                case "Mon":
                    progesssmon.setProgress(Integer.parseInt(a.getActivity_data()));
                    minmon.setText(new StringBuffer().append(progesssmon.getProgress()/60).append("h"));
                    break;
                case "Tue":
                    progessstue.setProgress(Integer.parseInt(a.getActivity_data()));
                    mintue.setText(new StringBuffer().append(progessstue.getProgress()/60).append("h"));
                    break;
                case "Wen":
                    progessswen.setProgress(Integer.parseInt(a.getActivity_data()));
                    minwen.setText(new StringBuffer().append(progessswen.getProgress()/60).append("h"));
                    break;
                case "Thu":
                    progesssthu.setProgress(Integer.parseInt(a.getActivity_data()));
                    minthu.setText(new StringBuffer().append(progesssthu.getProgress()/60).append("h"));
                    break;
                case "Fri":
                    progesssfri.setProgress(Integer.parseInt(a.getActivity_data()));
                    minfri.setText(new StringBuffer().append(progesssfri.getProgress()/60).append("h"));
                    break;
                case "Sat":
                    progessssat.setProgress(Integer.parseInt(a.getActivity_data()));
                    minsat.setText(new StringBuffer().append(progessssat.getProgress()/60).append("h"));
                    break;
            }
        }
    }
    public  String dateToWeek(String datetime) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"Sun", "Mon", "Tue", "Web", "Thu", "Wen", "Sat"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public void getView() {

        l1= (LinearLayout) findViewById(R.id.view_weekoccupy);
        l2=(LinearLayout)findViewById(R.id.view_lastoccupy);
        l3=(LinearLayout)findViewById(R.id.view_totaloccupy);
        lineView2 =(LineView)findViewById(R.id.lineView2);
        barCharts= findViewById(R.id.barchar);
        lineView=(LineView) findViewById(R.id.lineView);
        back = (ImageView) findViewById(R.id.acdetails_back);
        progessssun = (ProgressBar) findViewById(R.id.progesss1);
        progesssmon=(ProgressBar) findViewById(R.id.progesss2);
        progessstue =(ProgressBar) findViewById(R.id.progesss3);
        progessswen=(ProgressBar) findViewById(R.id.progesss4);
        progesssthu=(ProgressBar) findViewById(R.id.progesss5);
        progesssfri=(ProgressBar) findViewById(R.id.progesss6);
        progessssat=(ProgressBar) findViewById(R.id.progesss7);

        minsun = (TextView) findViewById(R.id.min1);
        minmon= (TextView) findViewById(R.id.min2);
        mintue= (TextView) findViewById(R.id.min3);
        minwen= (TextView) findViewById(R.id.min4);
        minthu= (TextView) findViewById(R.id.min5);
        minfri= (TextView) findViewById(R.id.min6);
        minsat= (TextView) findViewById(R.id.min7);
        linearLayout = (LinearLayout) findViewById(R.id.topbar);

    }
    public void setListener(){
        back.setOnClickListener(new MyListener());
    }
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
    public void setColor(){
        String colorstring= getIntent().getStringExtra("colortype");
        if(colorstring==null){
            color =getResources().getIdentifier("bg", "color", getPackageName());
        }else {
            color =getResources().getIdentifier(colorstring, "color", getPackageName());
        }
        linearLayout.setBackgroundColor(color);
        l1.setBackgroundColor(color);
        l2.setBackgroundColor(color);
        l3.setBackgroundColor(color);

    }



    //向服务器发送数据
    private void sendMessage() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ip);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    if(null!=info) {
//                        Log.e("test", info);
                        wrapperMessage(info);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
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
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if(linearLayout==null){
            Log.e("liner","null");
            return ;
        }
        if(scrollState == ScrollState.UP){

        }else if(scrollState == ScrollState.UP){

        }
    }


}

