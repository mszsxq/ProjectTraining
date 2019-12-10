package com.example.catchtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.fragment.MyAdapterActivities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.List;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import static com.mob.MobSDK.getContext;

public class Add_Page_Activity extends AppCompatActivity {
    private Handler handler;
    private int id;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiesfragment);
        final List<Activity> lists=new ArrayList<>();
        Intent intent  = getIntent();
        listView=findViewById(R.id.listview);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        id = sp.getInt("user_id",0);
        ImageView imageView=findViewById(R.id.addactivity);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Add_Page_Activity.this, AddActivityDetial.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        sendMessage();

        handler=new Handler() {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String info=(String)msg.obj;
                Type type=new TypeToken<List<Activity>>(){}.getType();
                Gson gson=new Gson();
                List<Activity> list = gson.fromJson(info,type);
                Log.e("error",list.toString());
                for(int i=0;i<list.size();i++){
                    Activity activity=new Activity();
                    activity.setIcon_name(list.get(i).getIcon_name());
                    activity.setIcon_id(list.get(i).getIcon_id());
                    String str=list.get(i).getIcon_name();
                    int img=getDrawableID(str);
                    activity.setImage(img);
                    activity.setActivity_name(list.get(i).getActivity_name());
                    activity.setActivity_id(list.get(i).getActivity_id());
                    String string=new String();
                    string=list.get(i).getIcon_color();
                    int color=getColorID(string);
                    activity.setIcon_color(list.get(i).getIcon_color());
                    activity.setColor(color);
                    lists.add(activity);
                }
                MyAdapterActivities myAdapterActivities = new MyAdapterActivities(getApplication(),lists,R.layout.activitiesfragment_litem);
                listView.setAdapter(myAdapterActivities);
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(Add_Page_Activity.this, Add_detailPage.class);
                Gson gson = new Gson();
                String ac = gson.toJson(lists.get(position));
                intent.putExtra("activity",ac);
                setResult(4,intent);
                finish();
            }
        });
    }
    private void sendMessage() {
//        Gson gson = new Gson();
//        String client = gson.toJson(id);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivityController?userid="+id);
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

    private int getDrawableID(String str) {
        try {
            String name = str;
            Field field = R.drawable.class.getField(name);
            int DrawableID = 0;
            DrawableID = field.getInt(new R.drawable());
            return DrawableID;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //由颜色名称转换为资源文件
    private int getColorID(String str){
        String color=str;
        try {
            Field field=R.color.class.getField(color);
            int ColorID=0;
            ColorID=field.getInt(new R.color());
            return ColorID;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return  0;
        }
    }
}
