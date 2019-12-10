package com.example.catchtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catchtime.entity.Location;
import com.example.catchtime.fragment.MyAdapterLocation;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import static com.mob.MobSDK.getContext;

public class Add_Page_Location extends AppCompatActivity {
    private List<Location> locations = new ArrayList<>();
    private MyAdapterLocation myAdapter;
    private Handler handler;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationfragment);
        listView = findViewById(R.id.loc_lv_local);
        final List<Location> locations=new ArrayList<>();
        getData();
        handler=new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Type type=new TypeToken<List<Location>>(){}.getType();
                Gson gson=new Gson();
                Log.e("infooo",info);
                List<Location> list = gson.fromJson(info.trim(),type);
                for(int i=0;i<list.size();i++){
                    Location location=new Location();
                    location.setLocationName(list.get(i).getLocationName());
                    location.setLocationId(list.get(i).getLocationId());
                    location.setLocationLng(list.get(i).getLocationLng());
                    location.setLocationLat(list.get(i).getLocationLat());
                    location.setLocationRange(list.get(i).getLocationRange());
                    location.setLocationDetail(list.get(i).getLocationDetail());
                    locations.add(location);
                }
                myAdapter = new MyAdapterLocation(getApplication(),locations,R.layout.item_location);
                listView.setAdapter(myAdapter);
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Gson gson = new Gson();
                Location location = locations.get(position);
                String locationnew  = gson.toJson(location);
                intent.putExtra("locationnew",locationnew);
                setResult(3,intent);
                finish();
            }
        });
        TextView addloc = findViewById(R.id.loc_btn_addloc);
        addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), AddLocation.class);
                startActivity(intent);
            }
        });
    }
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int userid=1;
                //?userid="+userid
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/LocationController");
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
