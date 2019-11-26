package com.example.catchtime.fragment;

<<<<<<< HEAD
import android.os.Build;
=======
import android.content.Context;
import android.content.Intent;
>>>>>>> 75ddcb55346238cc56cef283e7c085a8cd12796a
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.view.Window;
=======
import android.widget.Button;
>>>>>>> 75ddcb55346238cc56cef283e7c085a8cd12796a
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
<<<<<<< HEAD

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
=======
>>>>>>> 75ddcb55346238cc56cef283e7c085a8cd12796a
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第1个页面");
        view=inflater.inflate( R.layout.activitiesfragment,container,false);
        //存放数据的list；
        final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        listView=(ListView)view.findViewById(R.id.listview);
        Button button =view.findViewById(R.id.details);
        Button button1=view.findViewById(R.id.jump_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), ActivitiesDetail.class);
                startActivity(intent);
            }
        });
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
        list.add(map1);
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("img",R.drawable.paly);
        map2.put("name","游戏");
        list.add(map2);
        Map<String,Object> map3=new HashMap<String,Object>();
        map3.put("img",R.drawable.walk);
        map3.put("name","行走");
        list.add(map3);
        listView.setAdapter(new MyAdapterActivities(getActivity(),list,R.layout.activitiesfragment_litem));
        return view;
    }
}
