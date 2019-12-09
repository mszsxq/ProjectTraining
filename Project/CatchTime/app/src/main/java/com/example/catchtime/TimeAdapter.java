package com.example.catchtime;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.catchtime.entity.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeAdapter extends BaseAdapter {
    private Context context;
    private List<Time> contents = new ArrayList<>();
    private int itemLayoutId;
    private String zhi;
//    private List<Time> oldtime = new ArrayList<>();
//    private String oldt;

    public TimeAdapter(Context context, List<Time> contents, int itemLayoutId) {
        this.context = context;
        this.contents = contents;
        this.itemLayoutId = itemLayoutId;
//        this.oldtime = oldtime;
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
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        TextView tvName = convertView.findViewById(R.id.tx_begin);
        tvName.setText(contents.get(position).getBegin_time());
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);
                Log.e("弹出时间框","准备");
                Log.e("点击post", String.valueOf(position));
//                oldt = oldtime.get(0).getBegin_time();
//                Log.e("old2",oldt);
                TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
//                        oldt = oldtime.get(position).getBegin_time();
//                        Log.e("old2",oldt);
                        if(hour<10 && min<10){
                            contents.get(position).setBegin_time("0"+hour+":"+"0"+min);
//                            oldt = oldtime.get(position).getBegin_time();
//                            Log.e("old111111",oldt);
                        }else if (hour<10){
                            contents.get(position).setBegin_time("0"+hour+":"+min);
//                            oldt = oldtime.get(position).getBegin_time();
//                            Log.e("old111111",oldt);
                        }else if (min<10){
                            contents.get(position).setBegin_time(hour+":"+"0"+min);
//                            oldt = oldtime.get(position).getBegin_time();
//                            Log.e("old111111",oldt);
                        }else {
                            contents.get(position).setBegin_time(hour+":"+min);
//                            oldt = oldtime.get(position).getBegin_time();
//                            Log.e("old111111",oldt);
                        }
                        zhi = contents.get(position).getBegin_time();
                        Log.e("stat",zhi);
                        notifyDataSetChanged();
                    }
                };
                TimePickerDialog dialog1 = new TimePickerDialog(context,TimePickerDialog.THEME_HOLO_LIGHT,listener1,hour,min,true);
                dialog1.show();
                Log.e("弹出时间框","准备");
                contents.get(position).setFlog(1);
                Log.e("postion", String.valueOf(position)+contents.get(position).getFlog());
            }
        });
        return convertView;
    }
}
