package com.example.catchtime.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.catchtime.AddLocation;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;


import com.example.catchtime.entity.Location;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class LocationsFragment extends Fragment {

    private List<Location> locations = new ArrayList<>();
    private MyAdapterLocation myAdapter;
    private Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第2个页面");
        View view=inflater.inflate(R.layout.locationfragment,null);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.green));
        //存放数据的list
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
                    locations.add(location);
                }
                myAdapter = new MyAdapterLocation(getActivity(),locations,R.layout.item_location);
                ListView listView = view.findViewById(R.id.loc_lv_local);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ActivitiesDetail.class);
                        startActivity(intent);
                    }
                });
            }
        };
        TextView addloc = view.findViewById(R.id.loc_btn_addloc);
        addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddLocation.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int userid=1;
                //?userid="+userid
                try {
                    URL url = new URL("http://192.168.43.65:8080/Catchtime/LocationController");
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
