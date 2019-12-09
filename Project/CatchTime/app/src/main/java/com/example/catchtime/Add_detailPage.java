package com.example.catchtime;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.content.Intent;
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
    private TextView btnbegin;
    private TextView btnover;
    private TextView locationView;
    private TextView activityView;
    private ImageView imgActivity;
    private ImageView imgLocation;
    private ImageView okActivity;
    private ImageView acImg;
    private Calendar cal;
    private String zhi;
    private int hour,min;
    private int numhour=0;
    private int nummin=0;
    private int user_id=1;
    private String chuan_activity_name="xuexi";
    private int activity_id;
    private int location_id;
    private int two = 0;
    private String location_name;
    private String time="2019-12-07";
    private String timenow;
    private String allstarts;
    private String allends;
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
        user_id=1;
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
//                    }else{
//                     //如果不是当天的activity
//                     //非法输入弹框
//                        if (dstart.before(dallstart) || dend.after(dallend)){
//                            Toast.makeText(getApplicationContext(),"时间越界",Toast.LENGTH_SHORT).show();
//                        }else {
//                            //开始时间相同
//                            if ((dallstart.compareTo(dstart)) == 0) {
//                                if ((dallend.compareTo(dend) == 0)) {
//                                    //结束时间相同
//                                    Time timeall = new Time();
//                                    timeall.setBegin_time(allstarts);
//                                    timeall.setFinish_time(allstarts);
//                                    Time actime = new Time();
//                                    actime.setBegin_time(allstarts);
//                                    actime.setFinish_time(allends);
//                                    long sum = (dend.getTime() - dallend.getTime()) / 600000;
//                                    long num = (dstart.getTime() - dend.getTime()) / 600000;
//                                    timeMessageOldBeforeActivityOne(chuan_activity_name,timeall,user_id,sum);
//                                    timeMessageOldNewActivity(activity_id, location_id, actime,num,user_id);
//                                    finish();
//
//                                } else {
//                                    //结束时间不同
//                                    Time timeall = new Time();
//                                    timeall.setBegin_time(end);
//                                    timeall.setFinish_time(allends);
//                                    Time actime = new Time();
//                                    actime.setBegin_time(start);
//                                    actime.setFinish_time(end);
//                                    long sum = (dstart.getTime() - dend.getTime()) / 600000;
//                                    long num = (dend.getTime() - dallend.getTime()) / 600000;
//                                    timeMessageOldBeforeActivityOne(chuan_activity_name,timeall,user_id,sum);
//                                    timeMessageOldNewActivity(activity_id, location_id, actime, num,user_id);
//                                    finish();
//                                }
//                            } else {
//                                //开始时间不同
//                                if ((dallend.compareTo(dend) == 0)) {
//                                    //结束时间相同
//                                    Time timeall = new Time();
//                                    timeall.setBegin_time(allstarts);
//                                    timeall.setFinish_time(start);
//                                    Time actime = new Time();
//                                    actime.setBegin_time(start);
//                                    actime.setFinish_time(allends);
//                                    long sum = (dallstart.getTime() - dstart.getTime()) / 600000;
//                                    long num = (dstart.getTime() - dend.getTime()) / 600000;
//                                    timeMessageOldBeforeActivityOne(chuan_activity_name,timeall,user_id,sum);
//                                    timeMessageOldNewActivity(activity_id, location_id, actime,num,user_id);
//                                    finish();
//                                }else{
//                                    //结束时间不同
//                                    Time timeall1 = new Time();
//                                    timeall1.setBegin_time(allstarts);
//                                    timeall1.setFinish_time(start);
//                                    Time timeall2 = new Time();
//                                    timeall2.setBegin_time(end);
//                                    timeall2.setFinish_time(allends);
//                                    Time actime = new Time();
//                                    actime.setBegin_time(start);
//                                    actime.setFinish_time(end);
//                                    long sum1=(dallstart.getTime()-dstart.getTime())/600000;
//                                    long sum2=(dend.getTime()-dallend.getTime())/600000;
//                                    long sum = sum1+sum2;
//                                    long num= (dstart.getTime()-dend.getTime())/600000;
//                                    timeMessageOldBeforeActivityMuch(chuan_activity_name,timeall1,timeall2,user_id,sum);
//                                    timeMessageOldNewActivity(activity_id,location_id,actime,num,user_id);
//                                    finish();
//                                }
//                            }
//                                }
//                            }
//
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
    private void getnowtime(){
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd");//对日期进行格式化
        timenow = df.format(new Date());
        Log.e("timenow",timenow);
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tx_begin:
//                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
//                        if(hour<10 && min<10){
//                            btnbegin.setText("0"+hour+":"+"0"+min);
//                        }else if (hour<10){
//                            btnbegin.setText("0"+hour+":"+min);
//                        }else if (min<10){
//                            btnbegin.setText(hour+":"+"0"+min);
//                        }else {
//                            btnbegin.setText(hour+":"+min);
//                        }
//                    }
//                };
//                TimePickerDialog dialog = new TimePickerDialog(Add_detailPage.this,TimePickerDialog.THEME_HOLO_LIGHT,listener,hour,min,true);
//                dialog.show();
//                break;
//            case R.id.tx_end:
//                TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
//                        if(hour<10 && min<10){
//                            btnover.setText("0"+hour+":"+"0"+min);
//                        }else if (hour<10){
//                            btnover.setText("0"+hour+":"+min);
//                        }else if (min<10){
//                            btnover.setText(hour+":"+"0"+min);
//                        }else {
//                            btnover.setText(hour+":"+min);
//                        }
//                    }
//                };
//                TimePickerDialog dialog1 = new TimePickerDialog(Add_detailPage.this,TimePickerDialog.THEME_HOLO_LIGHT,listener1,hour,min,true);
//                dialog1.show();
//                break;
//            default:
//                break;
//        }
//    }

    //---------------------------------------------------发送time和activity_name找时间
    private void sendMessage() {
        chuan_activity_name="xuexi";
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.81:8080/Catchtime/DetailController?acname="+chuan_activity_name+"&&userid="+user_id+"&&time="+time);
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
        chuan_activity_name="xuexi";
        Gson gson = new Gson();
        String list = gson.toJson(timeList);
        Log.e("发数据啦","准备！");
        Log.e("time",list);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.81:8080/Catchtime/DetailSaveContrallor?acname="+chuan_activity_name+"&&userid="+user_id+"&&time="+time+"&&timelist="+list+"&&acid="+activity_id+"&&loid="+location_id);
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
