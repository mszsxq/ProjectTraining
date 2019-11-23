package com.example.catchtime.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

class MyAdapterActivities extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private int itemLayout;
    public MyAdapterActivities(Context context, List<Map<String,Object>> data,int itemLayout){
        this.context=context;
        this.data=data;
        this.itemLayout=itemLayout;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
