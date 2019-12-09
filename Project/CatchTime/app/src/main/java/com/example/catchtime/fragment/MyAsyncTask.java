package com.example.catchtime.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.example.catchtime.entity.chartData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAsyncTask extends AsyncTask<String,Void, ArrayList<PieEntry>> {
    private int position;
    private Context context;
    private RelativeLayout layout;
    private ViewGroup viewGroup;
    private PieChart pieChart;
    private String info;
    private int user_id;
    private int size;
    private int type;
    private String date;
    private ArrayList<Integer> colors = new ArrayList<>();
    public MyAsyncTask(int position, Context context, RelativeLayout layout, ViewGroup viewGroup, PieChart pieChart,int user_id,int size,int type,String date){
        this.context=context;
        this.position=position;
        this.layout=layout;
        this.viewGroup = viewGroup;
        this.pieChart=pieChart;
        this.size=size;
        this.user_id = user_id;
        this.type=type;
        this.date =date;
    }
    @Override
    protected void onPostExecute(ArrayList<PieEntry> entries) {
        super.onPostExecute(entries);
        new InitPieChart(pieChart, entries, context, layout.findViewById(R.id.add), layout.findViewById(R.id.change), layout.findViewById(R.id.data),
                layout.findViewById(R.id.linear), position, 0, colors,true,size,type);
        viewGroup.addView(layout);
    }

    @Override
    protected ArrayList<PieEntry> doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL("http://192.168.42.184:8080/Catchtime/showCart?position="+position+"&size="+size+"&user_id="+user_id+"&type="+type+"&date="+date+"");
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            info = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<chartData> list = gson.fromJson(info, new TypeToken<List<chartData>>() {}.getType());
        ArrayList<PieEntry> entries=new ArrayList<>();
        Log.i("检测","进入"+list.size());
        for(int i=0;i<list.size();i++) {
            int time = 0;
            String[] sp = (list.get(i).getTime()).split("-");
            int hour =Integer.parseInt(sp[0]) * 60;
            int minute = Integer.parseInt(sp[1]);
            time = hour + time;
            PieEntry pieEntry = new PieEntry(time, list.get(i).getName() + "/" + list.get(i).getTime(), context.getResources().getDrawable(getDrawableID(list.get(i).getIcon())));
            entries.add(pieEntry);
            colors.add(context.getResources().getColor(getColorID(list.get(i).getColor())));
            Log.i("检测", hour + "-" + minute + "-" + time);
            Log.i("检测", "colors" + list.get(i).getColor());
        }
        return entries;
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
    private int getColorID(String str) {
        try {
            String name = str;
            Field field = R.color.class.getField(name);
            int ColorID = 0;
            ColorID = field.getInt(new R.drawable());
            return ColorID;
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
