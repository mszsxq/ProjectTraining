package com.example.catchtime.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
=======
import android.os.SystemClock;
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Marker;
<<<<<<< HEAD
import com.example.catchtime.Add_Page_Activity;
import com.example.catchtime.R;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.Icon;
import com.example.catchtime.fragment.MyAdapterActivities;
=======
import com.example.catchtime.AddLocation;
import com.example.catchtime.R;
import com.example.catchtime.fragment.LocationsFragment;
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
<<<<<<< HEAD
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
=======
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
public class AddActivityDetial extends SwipeBackActivity {
    private GridView mGridView;
    public MyAdapter mMyadapter;
<<<<<<< HEAD
    private List<Icon> names;
    private TextView btnfin;
    private TextView btnex;
    private Handler handler;
    private EditText editText;
    private String activity_name;
    private Icon iconnew;
    private int count;
    private int pos=0;//返回最终选择了哪个图片
    private String id;
=======
    private List<String> names;
    private List<String> colorNames;
    private TextView btnfin;
    private TextView btnex;
    private EditText editText;
    private int locationId;
    private int activityId;
    private String activityName;
    private String locationName;
    private String detailName;
    private double lat;
    private double lng;
    private int pos=-1;//返回最终选择了哪个图片
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String info = (String) msg.obj;
            switch (msg.what){
                case 100:
                    locationId=Integer.parseInt(info);
                    Log.e("locationId",locationId+"");
                    break;
                case 200:
                    activityId=Integer.parseInt(info);
                    Log.e("activityId",activityId+"");
                    break;
                case 300:
                    Log.e("suc",info);
                    break;
            }
        }
    };
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivitydetial);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Intent intent=getIntent();
        locationName=intent.getStringExtra("locationName");
        detailName=intent.getStringExtra("detailName");
        lat = intent.getDoubleExtra("lat",0.00);
        lng = intent.getDoubleExtra("lng",0.00);
        names=new ArrayList<>();
<<<<<<< HEAD
        editText = (EditText) findViewById(R.id.ac_name);
        activity_name = editText.getText().toString();
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        sendMessage();
        //获取Icon内容
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String)msg.obj;
                if("添加成功".equals(info)){
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                }else{
                    Type listType=new TypeToken<List<Icon>>(){}.getType();
                    Gson gson=new Gson();
                    names = gson.fromJson(info,listType);
                    count = names.size();
                }
            }
        };
        iconnew = new Icon();
        mMyadapter=new MyAdapter(names,this);
=======
        names.add("walk");names.add("paly");names.add("walk");names.add("study");names.add("walk");names.add("study");names.add("walk");
        names.add("paly");names.add("study");names.add("paly");
        colorNames=new ArrayList<>();
        colorNames.add("main_orange");colorNames.add("main_body");colorNames.add("main_blue");colorNames.add("main_red");colorNames.add("main_blue_press");
        colorNames.add("text_color_1");colorNames.add("text_color_red");colorNames.add("title_color_blue");colorNames.add("red_5");colorNames.add("red_5");
        editText = (EditText) findViewById(R.id.etActivity);
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
        mMyadapter=new MyAdapter(names,colorNames,this);
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
        mGridView.setAdapter(mMyadapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyadapter=new MyAdapter(names,view.getContext(),position);
                mGridView.setAdapter(mMyadapter);
                pos=position;
                iconnew.setIcon_Id(position);
                iconnew.setIcon_address(names.get(pos).getIcon_address());
                iconnew.setColor(names.get(pos).getColor());
            }
        });

        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Intent intent = new Intent(AddActivityDetial.this, Add_Page_Activity.class);
                intent.putExtra("acname",activity_name);
                Gson gson1 = new Gson();
                String newAcIcon = gson1.toJson(iconnew);
                intent.putExtra("newAcIcon",newAcIcon);
                addMessage();
                startActivity(intent);
                finish();
=======

                activityName=editText.getText().toString();
                if(activityName.length()==0 || pos<0){
                    Toast.makeText(getApplicationContext(),"请输入活动名称,选择图片",Toast.LENGTH_LONG).show();
                }else if (detailName!=null){
                    sendToServer();
                    sendToServer1();
                    SystemClock.sleep(1000);
                    sendToServer2();
                    Intent intent1 = new Intent();
                    intent1.setClass(getApplicationContext(), AddLocation.class);
                    startActivity(intent1);
                }else {
                    sendToServer1();
                    finish();
                }
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
            }
        });

    }
    private void sendMessage() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.81:8080/Catchtime/IconController");
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
    private void addMessage() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Gson gson2 = new Gson();
                    Activity activitynew = new Activity();
                    activitynew.setIcon_id(iconnew.getIcon_Id());
                    activitynew.setActivity_name(activity_name);
                    //数据库数据数量加一为新活动的id
                    String clientnew = gson2.toJson(activitynew);
                    URL url = new URL("http://192.168.43.81:8080/Catchtime/ActivityController?client="+clientnew+"userId="+id);
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

    private void sendToServer2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String u="&activityId="+activityId+"&locationId="+locationId;
                    URL url = new URL("http://192.168.137.1:8080/Catchtime/ContactController?info=insert"+u);
                    Log.e("text","300000dsd");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("105",info);
                    wrapperMessage(info,300);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendToServer1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String u="&activityName="+activityName+"&iconId="+pos+1;
                    URL url = new URL("http://192.168.137.1:8080/Catchtime/ActivityController?info=insert"+u);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    activityId=Integer.parseInt(info);
                    Log.e("85",activityId+"");
                    wrapperMessage(info,200);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("dd","dee");
                }
            }
        }).start();
    }

    private void sendToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String u="&locationName="+locationName+"&detailName="+detailName+"&lat="+lat+"&lng="+lng;
                    URL url = new URL("http://192.168.137.1:8080/Catchtime/LocationController?info=insert"+u);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    locationId=Integer.parseInt(info);
                    Log.e("dd",locationId+"");
                    wrapperMessage(info,100);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("dd","dee");
                }
            }
        }).start();
    }

    private void wrapperMessage(String info, int i) {
        Message message = new Message();
        message.obj=info;
        message.what=i;
        handler.sendMessage(message);
    }
}
class MyAdapter extends BaseAdapter {
<<<<<<< HEAD
    private List<Icon> list;  //表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<Icon> list, Context context) {
=======
    private List<String> list;
    private List<String> colorList;//表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<String> list,List<String> colorList, Context context) {
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
        this.list = list;
        this.colorList=colorList;
        this.context = context;
    }
    public MyAdapter(List<Icon> list, Context context,int i) {
        this.list = list;
        this.context = context;
        this.i=i;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        Drawable cachedImage = null;
        if (null == convertView) {
            imageView = new ImageView(context);

            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Resources res=context.getResources();
<<<<<<< HEAD
        int picid = res.getIdentifier(list.get(position).getIcon_address(),"drawable",context.getPackageName());
=======
        int picid = res.getIdentifier(list.get(position),"drawable",context.getPackageName());
//        int color = res.getIdentifier(colorList.get(position),"color",context.getPackageName());
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
        imageView.setImageResource(picid);
//        imageView.setBackgroundColor(context.getResources().getColor(R.color.+colorList.get(p)));
        if (i==position){
            imageView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
//        imageView.setOnClickListener(new View.OnClickListener() {
//            //设置点击按钮
//            @Override
//            public void onClick(View v) {
//                imageView.setBackgroundColor(context.getResources().getColor(R.color.gray));
//                Log.e("sssssss","aaaaaaaaaaaa");
////                imageView.setBackground();
//            }
//        });
        return imageView;
    }
    class ViewHolder {
        ImageView img;
    }
}