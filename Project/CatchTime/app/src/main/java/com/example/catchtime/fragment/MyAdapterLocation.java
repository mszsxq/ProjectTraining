package com.example.catchtime.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catchtime.R;
<<<<<<< HEAD
import com.example.catchtime.entity.Location;


import com.example.catchtime.entity.Location;

import com.example.catchtime.entity.Locations;
=======


import com.example.catchtime.entity.Location;

import com.example.catchtime.entity.Locations;

>>>>>>> 850fe2f302fea9bb2c73c578e12c8222cacc877d

import java.util.ArrayList;
import java.util.List;

public class MyAdapterLocation extends BaseAdapter {
    private Context context;
    private List<Location> contents = new ArrayList<>();
    private int itemLayoutId;

<<<<<<< HEAD
=======
<<<<<<< HEAD
    public MyAdapterLocation(Context context, List<Location> contents, int itemLayoutId){
=======



>>>>>>> 850fe2f302fea9bb2c73c578e12c8222cacc877d
    public MyAdapterLocation(Context context, List<Location> contents, int itemLayoutId){

>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
        this.context = context;
        this.contents = contents;
        this.itemLayoutId = itemLayoutId;
    }
    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        TextView tvName = convertView.findViewById(R.id.loc_item_tv);
        tvName.setText(contents.get(position).getLocationName());
        return convertView;
    }
}

