package com.example.catchtime.fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.catchtime.R;
import java.util.List;
import java.util.Map;
import androidx.recyclerview.widget.RecyclerView;
class MyAdapterActivities extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private int itemLayoutId;
    private ImageView img_activity;
    private TextView name_activity;
    public MyAdapterActivities(Context context, List<Map<String,Object>> data,int itemLayoutId){
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
        }
        //获得每个item的对象
        img_activity=convertView.findViewById(R.id.img_activity);
        name_activity=convertView.findViewById(R.id.name_activity);

        img_activity.setImageResource((Integer)data.get(position).get("img"));
        img_activity.setBackgroundColor((Integer) data.get(position).get("bak_color"));
        name_activity.setText((String)data.get(position).get("name"));

        return convertView;
    }
}
