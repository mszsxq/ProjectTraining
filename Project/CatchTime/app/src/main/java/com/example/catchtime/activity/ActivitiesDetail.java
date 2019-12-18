package com.example.catchtime.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Base64;
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
    private int id;
    private ImageView imageView;
    private String activityName;
    private TextView textView;

//    private String ip="http://C:8080/Catchtime/ActivitiesDetailServlet?activity=cycle&id=1";
//    private String ip="http://10.7.89.247:8080/Catchtime/ActivitiesDetailServlet?activity=cycle&id=1";
//    private String ip="http://192.168.43.232:8080/Catchtime/ActivitiesDetailServlet?activity="+activityName+"&id="+id;

    private Handler handler;
    private ArrayList<All_data> list= new ArrayList<>();
    private List<All_data> listm= new ArrayList<>();
    private List<All_data> listw = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_detail);



        getView();
        setListener();
        init();
        activityName=getIntent().getStringExtra("activity_name");
        textView.setText(activityName);
        setColor();
        sendMessage();

        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String info = (String)msg.obj;
                Gson gson =new Gson();
                list =gson.fromJson(info,new TypeToken<List<All_data>>(){}.getType());
                listWeekly();
                listMonthly();
                showData();
            }
        };

    }

    private void init() {
        progesssmon.setProgress(0);
        progessstue.setProgress(0);
        progessswen.setProgress(0);
        progesssthu.setProgress(0);
        progesssfri.setProgress(0);
        progessssat.setProgress(0);
        progessssun.setProgress(0);
        minmon.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        mintue.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        minwen.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        minthu.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        minfri.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        minsat.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
        minsun.setText(new StringBuffer().append(progesssmon.getProgress()).append("h"));
    }

    private void showData() {


        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i=0;i<listw.size();i++){
            values.add(new BarEntry(i+1, Float.parseFloat(listw.get(i).getActivity_data())/60));
        }
        new InitBarChart((BarChart) barCharts,values,this);

        ArrayList<String>  xlist =new ArrayList<>();
        ArrayList<Float>  ylist =new ArrayList<>();
        for (All_data a :listm){
            Float data = (Float.parseFloat(a.getActivity_data()))/300;
            ylist.add(data);
            xlist.add(xListData(a));
        }
        lineView2.setData(xlist, ylist);

        ArrayList<String>  xlist1 =new ArrayList<>();
        ArrayList<Float>  ylist1 =new ArrayList<>();
        for (All_data a :listw){
            Float data = Float.parseFloat(a.getActivity_data())/300;
            ylist1.add(data);
            xlist1.add(xListData(a));
        }
        lineView.setData(xlist1, ylist1);
        progressBarShow();


    }
    public String xListData(All_data a){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        Date date =null;
        try {
            date = simpleDateFormat.parse(a.getData());
            ca.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int m =ca.get(Calendar.MONTH)+1;
        int d =ca.get(Calendar.DAY_OF_MONTH);
        return ""+m+"-"+d;
    }
    public void listMonthly(){
        listm.clear();
        for(int i=0;i<list.size();i++){
                if(i%2==0){
                    All_data all_data =list.get(i);
                    listm.add(all_data);
                }
            }
    }
    public void listWeekly(){
        listw.clear();
        int len = list.size();
        if(len>7){
            for(int i=list.size()-7;i< list.size();i++){
                All_data all_data =list.get(i);
                Log.e("listw",list.get(i).toString());
                listw.add(all_data);
            }
        }else{
            for(int i=0;i<list.size();i++){
                All_data all_data =list.get(i);
                Log.e("listw",list.get(i).toString());
                listw.add(all_data);
            }
        }

    }
    public void progressBarShow(){
        for(All_data a:listw){
            switch (dateToWeek(a.getData())){
                case "Sun":
                    Log.e("sun",a.getActivity_data());
                    progessssun.setProgress(Integer.parseInt(a.getActivity_data()));
                    minsun.setText(new StringBuffer().append(progessssun.getProgress()/60).append("h"));
                    break;
                case "Mon":
                    Log.e("mon",a.getActivity_data());
                    progesssmon.setProgress(Integer.parseInt(a.getActivity_data()));
                    minmon.setText(new StringBuffer().append(progesssmon.getProgress()/60).append("h"));
                    break;
                case "Tue":
                    Log.e("tue",a.getActivity_data());
                    progessstue.setProgress(Integer.parseInt(a.getActivity_data()));
                    mintue.setText(new StringBuffer().append(progessstue.getProgress()/60).append("h"));
                    break;
                case "Wen":
                    Log.e("wen",a.getActivity_data());
                    progessswen.setProgress(Integer.parseInt(a.getActivity_data()));
                    minwen.setText(new StringBuffer().append(progessswen.getProgress()/60).append("h"));
                    break;
                case "Thu":
                    Log.e("thu",a.getActivity_data());
                    progesssthu.setProgress(Integer.parseInt(a.getActivity_data()));
                    minthu.setText(new StringBuffer().append(progesssthu.getProgress()/60).append("h"));
                    break;
                case "Fri":
                    Log.e("fri",a.getActivity_data());
                    progesssfri.setProgress(Integer.parseInt(a.getActivity_data()));
                    minfri.setText(new StringBuffer().append(progesssfri.getProgress()/60).append("h"));
                    break;
                case "Sat":
                    Log.e("sat",a.getActivity_data());
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
        imageView = (ImageView) findViewById(R.id.ac_icon);
        l1= (LinearLayout) findViewById(R.id.view_weekoccupy);
        l2=(LinearLayout)findViewById(R.id.view_lastoccupy);
        l3= (LinearLayout) findViewById(R.id.view_monthlyoccupy);
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
        textView = (TextView) findViewById(R.id.activity);
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
        if(null!=getIntent()){
            int color= getIntent().getIntExtra("color",0);
            int color1= getIntent().getIntExtra("color1",0);
            if(color!=0){
                linearLayout.setBackgroundColor(color);
                l1.setBackgroundColor(color);
                l2.setBackgroundColor(color);
                l3.setBackgroundColor(color);
            }else if(color1!=0) {
                linearLayout.setBackgroundColor(getResources().getColor(color1));
                l1.setBackgroundColor(getResources().getColor(color1));
                l2.setBackgroundColor(getResources().getColor(color1));
                l3.setBackgroundColor(getResources().getColor(color1));
            }else{
                linearLayout.setBackgroundColor(0);
                l1.setBackgroundColor(0);
                l2.setBackgroundColor(0);
                l3.setBackgroundColor(0);
            }
            int icon =getIntent().getIntExtra("icon",0);
            imageView.setImageResource(icon);
        }

    }



    //向服务器发送数据
    private void sendMessage() {
        SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
        id= p.getInt("user_id",0);


       Log.e("id11111111", String.valueOf(id));
       Log.e("activity11111111",activityName);

       Thread t= new Thread(){
            @Override
            public void run() {
                try {

                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivitiesDetailServlet?id="+id+"&&activity="+activityName);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();

                    if(null!=info) {
                        Log.e("test", info);
                        Message msg = Message.obtain();
                        msg.obj = info;
                        handler.sendMessage(msg);
                    }else if(null==info){
                        Log.e("info","未从服务器获取到数据");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        t.start();

    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
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

