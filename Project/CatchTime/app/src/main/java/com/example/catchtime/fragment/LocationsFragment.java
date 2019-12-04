package com.example.catchtime.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.catchtime.AddLocation;
import com.example.catchtime.NewPalce;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.entity.Location;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class LocationsFragment extends Fragment {

    private List<Location> locations = new ArrayList<>();
    private MyAdapterLocation myAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第2个页面");

        View view=inflater.inflate(R.layout.locationfragment,null);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.green));
        Location location = new Location();
        location.setLocName("河北师范大学软件学院");
        location.setLocThing("学院");
        locations.add(location);
        Location location1 = new Location();
        location1.setLocName("河北师范大学启智园");
        location1.setLocThing("宿舍");
        locations.add(location1);
        Location location2 = new Location();
        location2.setLocName("河北师范大学第三食堂");
        location2.setLocThing("第三食堂");
        locations.add(location2);
        Location location3 = new Location();
        location3.setLocName("河北师范大学西操场");
        location3.setLocThing("操场");
        locations.add(location3);
        for(int i = 0;i<10;i++){
         Location name = new Location();
         locations.add(name);
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
}
