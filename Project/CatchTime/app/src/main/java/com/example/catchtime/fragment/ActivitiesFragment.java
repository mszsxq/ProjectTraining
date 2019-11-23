package com.example.catchtime.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catchtime.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ActivitiesFragment extends Fragment {
    View view;
    private ListView listView;
    private MyAdapterActivities myAdapterActivities;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第1个页面");
        view=inflater.inflate( R.layout.activitiesfragment,container,false);
        //存放数据的list；
        final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        listView=(ListView)view.findViewById(R.id.listview);
        listView.setAdapter(new MyAdapterActivities(getActivity(),list,R.layout.activitiesfragment_litem));

        return view;
    }
}
