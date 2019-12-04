package com.example.catchtime.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.catchtime.AddLocation;
import com.example.catchtime.R;
import com.example.catchtime.fragment.LocationsFragment;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private List<String> names;
//    private List<String> colorNames;
    private TextView btnfin;
    private TextView btnex;
    private EditText editText;
//    private String iconName;
//    private String iconColor;
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
                case 200:
                    Log.e("ss",info);
            }
        }
    };
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
        names.add("onfeet");names.add("onfeet");names.add("onfeet");names.add("onfeet");names.add("onfeet");names.add("onfeet");names.add("onfeet");
        names.add("onfeet");names.add("onfeet");names.add("onfeet");
        editText = (EditText) findViewById(R.id.etActivity);
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
        mMyadapter=new MyAdapter(names,this);
        mGridView.setAdapter(mMyadapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyadapter=new MyAdapter(names,view.getContext(),position);
                mGridView.setAdapter(mMyadapter);
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

                activityName=editText.getText().toString();
                if(activityName.length()==0 || pos<0){
                    Toast.makeText(getApplicationContext(),"请输入活动名称,选择图片",Toast.LENGTH_LONG).show();
                }else if (detailName!=null){
                    sendToServer();
                    sendToServer1();
                    Intent intent1 = new Intent();
                    intent1.setClass(getApplicationContext(), AddLocation.class);
                    startActivity(intent1);
                }else {
                    sendToServer1();
                    finish();
                }
            }
        });
    }

    private void sendToServer1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String u="&activityName="+activityName+"&iconId="+pos+1;
                    URL url = new URL("http://192.168.137.1:8080/Catchtime/ActivityController?info=insert"+u);
                    Log.e("text","t");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("85",info);
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
                    Log.e("text","tt");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("dd",info);
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

    private void wrapperMessage(String info, int i) {
        Message message = new Message();
        message.obj=info;
        message.what=i;
        handler.sendMessage(message);
    }
}
class MyAdapter extends BaseAdapter {
    private List<String> list;  //表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public MyAdapter(List<String> list, Context context,int i) {
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
        int picid = res.getIdentifier(list.get(position),"drawable",context.getPackageName());
        imageView.setImageResource(picid);
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