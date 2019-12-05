package com.example.catchtime.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catchtime.R;


import com.example.catchtime.entity.Location;

import com.example.catchtime.entity.Locations;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterLocation extends BaseAdapter {
    private Context context;
    private List<Locations> contents = new ArrayList<>();
    private int itemLayoutId;


    public MyAdapterLocation(Context context, List<Locations> contents, int itemLayoutId){

    public MyAdapterLocation(Context context, List<Location> contents, int itemLayoutId){

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
