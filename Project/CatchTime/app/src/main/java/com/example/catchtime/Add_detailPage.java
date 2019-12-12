package com.example.catchtime;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.Location;
import com.example.catchtime.entity.Time;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import static com.mob.MobSDK.getContext;

public class Add_detailPage extends SwipeBackActivity {
    private TextView locationView;
    private TextView activityView;
    private ImageView imgActivity;
    private ImageView imgLocation;
    private ImageView okActivity;
    private ImageView acImg;
    private int user_id;
    private String chuan_activity_name;
    private int activity_id;
    private int location_id;
    private String location_name;
    private String time;
    private String timenow;
    private Handler handler;
    private List<Time> timeList;
    private ListView listViewStart;
    private ListView listViewEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_detail_page);
        timeList = new ArrayList<>();
        listViewStart = (ListView) findViewById(R.id.list_start);
        listViewEnd = (ListView) findViewById(R.id.list_end);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = sp.getInt("user_id",0);
        Log.e("id",user_id+"");
        Intent intent = getIntent();
        chuan_activity_name=intent.getStringExtra("activity_name");
        time = intent.getStringExtra("date");
        Log.e("name",chuan_activity_name);
        Log.e("time",time);
        sendMessage();
        getnowtime();
        //获取activity当前时间段
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String)msg.obj;
                String inforicon = info;
                if("修改成功".equals(info)){
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if("非法输入".equals(info)){
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Type type=new TypeToken<List<Time>>(){}.getType();
                    Gson gson=new Gson();
                    List<Time> list = gson.fromJson(info,type);
                    Log.e("error",list.toString());
                    for(int i=0;i<list.size();i++){
                        list.get(i).setFlog(0);
                        timeList.add(list.get(i));//my
                    }
                    listViewStart.setAdapter(new TimeAdapter(Add_detailPage.this,timeList,R.layout.startitem));
                    listViewEnd.setAdapter(new TimeAdapterEnd(Add_detailPage.this,timeList,R.layout.startitem));
//                    String[] starts = time.getBegin_time().split(" ");
//                    String[] ends = time.getFinish_time().split(" ");
//                    allstarts = time.getBegin_time();
//                    allends = time.getBegin_time();
//                    btnbegin.setText(starts[1]);
//                    btnover.setText(ends[1]);
                }
            }
        };
        //跳转activity和location的箭头
        imgActivity = (ImageView) findViewById(R.id.activity_jiantou);
        imgLocation = (ImageView) findViewById(R.id.address_jiantou);
        //新增activity的图片
        acImg = (ImageView) findViewById(R.id.iv1);
        //完成activity的对勾
        okActivity = (ImageView) findViewById(R.id.ok_activity);
        //location的textView
        locationView = (TextView) findViewById(R.id.et1);
        activityView = (TextView) findViewById(R.id.et2);
        //传递user_id，向activity详情页
        okActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("准备进入EC", "准备时间");
                sendMessageTime();
                Log.e("进入完成", "OK");
            }
        });

        //点击添加activity跳转详情页
        imgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("跳转页面前",String.valueOf(user_id));
                Intent intent  = new Intent(getApplicationContext(),Add_Page_Activity.class);
                intent.putExtra("id",user_id);
                startActivityForResult(intent,2);
            }
        });


        //点击添加location跳转详情页
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("跳转页面前",String.valueOf(user_id));
                Intent intent  = new Intent(getApplicationContext(),Add_Page_Location.class);
                intent.putExtra("id",user_id);
                startActivityForResult(intent,1);
            }
        });

    }

    private void getnowtime(){
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd");//对日期进行格式化
        timenow = df.format(new Date());
        Log.e("timenow",timenow);
    }


    //---------------------------------------------------发送time和activity_name找时间
    private void sendMessage() {
//        chuan_activity_name="xuexi";
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/DetailController?acname="+chuan_activity_name+"&&userid="+user_id+"&&time="+time);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    if(null!=info) {
                        Log.e("ww", info);
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
    private void sendMessageTime() {
//        chuan_activity_name="xuexi";
        Gson gson = new Gson();
        String list = gson.toJson(timeList);
        Log.e("发数据啦","准备！");
        Log.e("time",list);
        Log.e("chuanname", "sendMessageTime:name "+chuan_activity_name+"id"+location_id+"acid"+activity_id+time );
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/DetailSaveContrallor?acname="+chuan_activity_name+"&&userid="+user_id+"&&time="+time+"&&timelist="+list+"&&acid="+activity_id+"&&loid="+location_id);
                    Log.e("发送完数据啦","OK");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    if(null!=info) {
                        Log.e("ww", info);
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




    //------------------------------------------------------------------acivity和location页面返回数据判断
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String infolo = data.getStringExtra("locationnew");
            if(infolo!=null){
                Gson gsonlo = new Gson();
                Location locationnew = new Location();
                locationnew = gsonlo.fromJson(infolo,Location.class);
                locationView.setText(locationnew.getLocationName());
                location_id = locationnew.getLocationId();
                location_name = locationnew.getLocationName();
            }
        }
        if (requestCode == 2 && resultCode == 4){
            String ac_add = data.getStringExtra("activity");
            Activity activity = new Activity();
            Gson gson = new Gson();
            activity = gson.fromJson(ac_add,Activity.class);
            activity_id=activity.getActivity_id();
            Log.e("id", String.valueOf(activity_id));
            acImg.setImageResource(activity.getImage());
            acImg.setBackgroundColor(activity.getColor());
            activityView.setText(activity.getActivity_name());
        }
    }



}
