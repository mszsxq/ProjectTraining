package com.example.catchtime.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
