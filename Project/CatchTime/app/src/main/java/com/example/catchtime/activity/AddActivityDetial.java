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
import com.example.catchtime.Add_Page_Activity;
import com.example.catchtime.R;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.Icon;
import com.example.catchtime.fragment.MyAdapterActivities;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
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
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
public class AddActivityDetial extends SwipeBackActivity {
    private GridView mGridView;
    public MyAdapter mMyadapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivitydetial);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        names=new ArrayList<>();
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
                Intent intent = new Intent(AddActivityDetial.this, Add_Page_Activity.class);
                intent.putExtra("acname",activity_name);
                Gson gson1 = new Gson();
                String newAcIcon = gson1.toJson(iconnew);
                intent.putExtra("newAcIcon",newAcIcon);
                addMessage();
                startActivity(intent);
                finish();
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
}
class MyAdapter extends BaseAdapter {
    private List<Icon> list;  //表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<Icon> list, Context context) {
        this.list = list;
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
        int picid = res.getIdentifier(list.get(position).getIcon_address(),"drawable",context.getPackageName());
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