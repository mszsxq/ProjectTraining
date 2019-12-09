package com.example.catchtime.fragment;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
=======
import android.graphics.Bitmap;
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.catchtime.R;
import com.example.catchtime.entity.Activity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import androidx.recyclerview.widget.RecyclerView;
public class MyAdapterActivities extends BaseAdapter {
    private Context context;
    private List<Activity> data;
    private LayoutInflater layoutInflater;
    private int itemLayoutId;
    private ImageView img_activity;
    private TextView name_activity;
    public MyAdapterActivities(Context context, List<Activity> data,int itemLayoutId){
        this.context=context;
        this.data=data;
        this.itemLayoutId=itemLayoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null==convertView){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(itemLayoutId,null);
            img_activity=convertView.findViewById(R.id.img_activity);
            name_activity=convertView.findViewById(R.id.name_activity);
        }
        Log.e("1","ok?");
        //获得每个item的对象
        img_activity.setImageResource(data.get(position).getImage());
        img_activity.setBackgroundColor(data.get(position).getColor());
        name_activity.setText(data.get(position).getActivity_name());
        Log.e("2","ok?");
        Log.e("3",name_activity.getText().toString());
        Log.e("4",data.get(position).getIcon_name());
        Log.e("5",data.get(position).getIcon_color());
        return convertView;
    }
    private int getDrawableID(String str) {
        try {
            String name = str;
            Field field = R.drawable.class.getField(name);
            int DrawableID = 0;
            DrawableID = field.getInt(new R.drawable());
            return DrawableID;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
