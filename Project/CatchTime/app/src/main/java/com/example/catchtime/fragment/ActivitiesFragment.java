package com.example.catchtime.fragment;

import android.os.Build;
import android.os.Build;

import android.os.Build;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class ActivitiesFragment extends Fragment {
    View view;
    private ListView listView;
    private MyAdapterActivities myAdapterActivities;
    private Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第1个页面");
        view=inflater.inflate( R.layout.activitiesfragment,container,false);
        //存放数据的list；
        final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        listView=(ListView)view.findViewById(R.id.listview);
        Button button1=view.findViewById(R.id.jump_login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
        //准备数据源：
        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("img",R.drawable.study);
        map1.put("name","学习");
        map1.put("bak_color",R.color.gray);
        list.add(map1);
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("img",R.drawable.paly);
        map2.put("name","游戏");
        map2.put("bak_color",R.color.red_2);
        list.add(map2);
        Map<String,Object> map3=new HashMap<String,Object>();
        map3.put("img",R.drawable.walk);
        map3.put("name","行走");
        map3.put("bak_color",R.color.colorAccent);
        list.add(map3);
        listView.setAdapter(new MyAdapterActivities(getActivity(),list,R.layout.activitiesfragment_litem));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitiesDetail.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
