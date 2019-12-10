package com.example.catchtime.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catchtime.Login;
import com.example.catchtime.ModifyPage;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.entity.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
public class ClickLocationFragment extends AppCompatActivity {
    private ListView listView;
    private MyAdapterActivities myAdapterActivities;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.change_alllocations);
        final List<String> lists=new ArrayList<>();

        getData();
        handler=new Handler() {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String info=(String)msg.obj;
                    Type type=new TypeToken<List<String>>(){}.getType();
                Gson gson=new Gson();
                List<String> list = gson.fromJson(info,type);
                Log.e("error",list.toString());

                for(int i=0;i<list.size();i++){
                    String name = list.get(i);
                    lists.add(name);
                }
                Log.e("info",info);
                listView=findViewById(R.id.listview);
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,lists);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ModifyPage.class);
                        intent.putExtra("locationName",lists.get(position));
                        setResult(300,intent);
                        finish();
                    }
                });

            }
        };


    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int userid=1;
                try {
                    URL url = new URL("http://192.168.42.184:8080/Catchtime/LocationFind?userid="+userid+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("wer", "df" + info);
                    wrapperMessage(info);
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
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
