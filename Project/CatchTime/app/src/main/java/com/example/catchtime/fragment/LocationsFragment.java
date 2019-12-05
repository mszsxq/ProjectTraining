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
<<<<<<< HEAD
=======

import com.example.catchtime.entity.Locations;
>>>>>>> f814d9a6ed1f52f622f796c1cb7a4a35699cb472


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
<<<<<<< HEAD
=======

>>>>>>> f814d9a6ed1f52f622f796c1cb7a4a35699cb472
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class LocationsFragment extends Fragment {
<<<<<<< HEAD

    private List<Location> locations = new ArrayList<>();
=======


    private List<Locations> locations = new ArrayList<>();

>>>>>>> f814d9a6ed1f52f622f796c1cb7a4a35699cb472
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
<<<<<<< HEAD
=======
        Locations locations = new Locations();
        locations.setLocName("河北师范大学软件学院");
        locations.setLocThing("学院");
        this.locations.add(locations);
        Locations locations1 = new Locations();
        locations1.setLocName("河北师范大学启智园");
        locations1.setLocThing("宿舍");
        this.locations.add(locations1);
        Locations locations2 = new Locations();
        locations2.setLocName("河北师范大学第三食堂");
        locations2.setLocThing("第三食堂");
        this.locations.add(locations2);
        Locations locations3 = new Locations();
        locations3.setLocName("河北师范大学西操场");
        locations3.setLocThing("操场");
        this.locations.add(locations3);
        for(int i = 0;i<10;i++){
         Locations name = new Locations();
         this.locations.add(name);
        }
        myAdapter = new MyAdapterLocation(getActivity(), this.locations,R.layout.item_location);
        ListView listView = view.findViewById(R.id.loc_lv_local);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitiesDetail.class);
                startActivity(intent);

>>>>>>> f814d9a6ed1f52f622f796c1cb7a4a35699cb472
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
<<<<<<< HEAD
=======

>>>>>>> f814d9a6ed1f52f622f796c1cb7a4a35699cb472
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
