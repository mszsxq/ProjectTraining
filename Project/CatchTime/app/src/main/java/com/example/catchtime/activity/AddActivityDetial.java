package com.example.catchtime.activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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
import com.example.catchtime.Add_Page_Activity;
import com.example.catchtime.R;
import com.example.catchtime.fragment.MyAdapterActivities;
import com.example.catchtime.AddLocation;
import com.example.catchtime.entity.Icon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
public class AddActivityDetial extends SwipeBackActivity {
    private GridView mGridView;
    private MyAdapter mMyadapter;
    private List<Icon> icons=new ArrayList<>();
    private List<String> names =new ArrayList<>();
    private List<String> colorNames=new ArrayList<>();
    private TextView btnfin;
    private TextView btnex;
    private EditText editText;
//    private Icon iconnew;
    private int count;
    private int pos=0;//返回最终选择了哪个图片
    private int userId;
    private int locationId;
    private int activityId;
    private String activityName;
    private String locationName;
    private String detailName;
    private double lat;
    private double lng;
    private Handler handler;
    private Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivitydetial);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        sendToServer5();
        handler=new Handler(){
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
                    case 400:
                        List<Icon>list=new ArrayList<>();
                        try {
                            JSONArray jsonArray=new JSONArray(info);
                            for(int j=0;j<jsonArray.length();j++){
                                String string=jsonArray.getString(j);
                                JSONObject jsonObject=new JSONObject(string);
                                Icon icon =new Icon(jsonObject.getInt("Icon_Id")
                                        ,jsonObject.getString("Icon_address"),jsonObject.getString("Color"));
                                list.add(icon);
                            }
                            for(int i=0;i<list.size();i++){
                                Icon icon =list.get(i);
                                icon.setIconId(list.get(i).getIconId());
                                icon.setIconPic(list.get(i).getIconPic());
                                icon.setIconColor(list.get(i).getIconColor());
                                names.add(list.get(i).getIconPic());
                                colorNames.add(list.get(i).getIconColor());
                                Log.e("colorList",colorNames.toString());
                                icons.add(icon);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        Intent intent=getIntent();
        locationName=intent.getStringExtra("locationName");
        detailName=intent.getStringExtra("detailName");
        lat = intent.getDoubleExtra("lat",0.00);
        lng = intent.getDoubleExtra("lng",0.00);
        editText = (EditText) findViewById(R.id.ac_name);
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
//        id = intent.getStringExtra("id");
//        sendMessage();
        //获取Icon内容
//        handler1= new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                String info = (String)msg.obj;
//                if("添加成功".equals(info)){
//                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
//                }else{
//                    Type listType=new TypeToken<List<Icon>>(){}.getType();
//                    Gson gson=new Gson();
//                    names = gson.fromJson(info,listType);
//                    count = names.size();
//                }
//            }
//        };
//        iconnew = new Icon();
        editText = (EditText) findViewById(R.id.ac_name);
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
        mMyadapter=new MyAdapter(names,colorNames,this);
        mGridView.setAdapter(mMyadapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyadapter=new MyAdapter(names,colorNames,view.getContext(),position);
                mGridView.setAdapter(mMyadapter);
                pos=icons.get(position).getIconId();
                Log.e("pos",pos+"");
                pos=position;
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
//                Intent intent = new Intent(AddActivityDetial.this, Add_Page_Activity.class);
//                intent.putExtra("acname",activity_name);
//                Gson gson1 = new Gson();
//                String newAcIcon = gson1.toJson(iconnew);
//                intent.putExtra("newAcIcon",newAcIcon);
//                addMessage();
//                startActivity(intent);
//                finish();

                activityName=editText.getText().toString();
                if(activityName.length()==0 || pos<0){
                    Toast.makeText(getApplicationContext(),"请输入活动名称,选择图片",Toast.LENGTH_LONG).show();
                }else if (detailName!=null){
                    sendToServer1();
                    sendToServer2();
//                    SystemClock.sleep(1000);
                    sendToServer3();
                    Intent intent1 = new Intent();
                    intent1.setClass(getApplicationContext(), AddLocation.class);
                    startActivity(intent1);
                }else {
                    sendToServer2();
                    finish();
                }
            }
        });

    }
//    private void sendMessage() {
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://175.24.14.26:8080/Catchtime/IconController");
//                    URLConnection conn = url.openConnection();
//                    InputStream in = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
//                    String info = reader.readLine();
//                    if(null!=info) {
//                        Log.e("ww", info);
//                        wrapperMessage(info);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//    private void addMessage() {
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Gson gson2 = new Gson();
//                    Activity activitynew = new Activity();
//                    activitynew.setIcon_id(iconnew.getIconId());
//                    activitynew.setActivity_name(activity_name);
//                    //数据库数据数量加一为新活动的id
//                    String clientnew = gson2.toJson(activitynew);
//                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivityController?client="+clientnew+"userId="+id);
//                    URLConnection conn = url.openConnection();
//                    InputStream in = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
//                    String info = reader.readLine();
//                    if(null!=info) {
//                        Log.e("ww", info);
//                        wrapperMessage(info);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//    private void wrapperMessage(String info){
//        Message msg = Message.obtain();
//        msg.obj = info;
//        handler.sendMessage(msg);
//    }

    private void sendToServer4() {
        String u1 = "http://175.24.14.26:8080/Catchtime/ActivityController?info=insert&activityName="+activityName +"&iconId=" + pos+"&userId="+userId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(1000);
                    URL url = new URL(u1);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info, 300);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendToServer5() {
        String u="http://175.24.14.26:8080/Catchtime/IconfindAll";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(1000);
                    URL url = new URL(u);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info,400);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendToServer3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Log.e("loc",locationId+"");
                    Log.e("act",activityId+"");
                    String u2="http://175.24.14.26:8080/Catchtime/ContactInsert?info=insert&activityId="+activityId+"&locationId="+locationId+"&userId="+userId;
                    URL url = new URL(u2);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info,300);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendToServer2() {
        String u1="http://175.24.14.26:8080/Catchtime/ActivityController?info=insert&activityName="+activityName+"&iconId="+pos+"&userId="+userId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(1000);
                    URL url = new URL(u1);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info,200);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendToServer1() {
        String u="http://175.24.14.26:8080/Catchtime/LocationInsert?info=insert&locationName="+locationName+"&detailName="+detailName+"&lat="+lat+"&lng="+lng+"&userId="+userId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(u);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info,100);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
//    public MyAdapter(List<Icon> list, Context context) {}
    private List<String> list;
    private List<String> colorList;//表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<String> list,List<String> colorList, Context context) {
        this.list = list;
        this.colorList=colorList;
        this.context = context;
    }
    public MyAdapter(List<String> list, List<String> colorList,Context context,int i) {
        this.list = list;
        this.colorList=colorList;
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
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (null == convertView) {
            imageView = new ImageView(context);

            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Resources res=context.getResources();
        int picid = res.getIdentifier(list.get(position),"drawable",context.getPackageName());
            imageView.setImageResource(picid);
            imageView.setBackgroundColor(getColorID(colorList.get(position)));
        imageView.setImageResource(picid);
        if (i==position){
            imageView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
        return imageView;
    }
    private int getColorID(String str) {
        String color = str;
        Log.e("ccc",str);
        try {
            Field field = R.color.class.getField(color);
            int ColorID = 0;
            ColorID = field.getInt(new R.color());
            return ColorID;//colorID就是R.color.name
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }
}