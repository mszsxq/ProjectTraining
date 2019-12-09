package com.example.catchtime.fragment;

import android.os.Build;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.entity.Activity;

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
        final List<Activity> list=new ArrayList<Activity>();
        listView=(ListView)view.findViewById(R.id.listview);
        ImageView imageView=view.findViewById(R.id.addactivity);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), AddActivityDetial.class);
                startActivity(intent);
            }
        });
        Button button1=view.findViewById(R.id.jump_login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(new MyAdapterActivities(getActivity(),list,R.layout.activitiesfragment_litem));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitiesDetail.class);
                intent.putExtra("colortype",list.get(position).getIcon_name());
                startActivity(intent);
            }
        });
        return view;
    }
}
