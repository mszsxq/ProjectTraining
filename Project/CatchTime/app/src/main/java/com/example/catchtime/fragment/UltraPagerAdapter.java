/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package com.example.catchtime.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catchtime.ModifyPage;
import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.example.catchtime.chart.PerPieEntry;
import com.example.catchtime.entity.chartData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

//import android.support.v4.view.PagerAdapter;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private ArrayList<PieEntry> entries=new ArrayList<>();
    private String info=null;
    private RelativeLayout linearLayout;
    private boolean isMultiScr;
    private int flag=0;
    private Context context;
    private List<PerPieEntry> list;
    private PieChart pieChart;
    private boolean check;
    private int type;
    private String date;
    private ArrayList<Integer> colors = new ArrayList<>();
    private Handler   handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {


        }

    };
    public UltraPagerAdapter(boolean isMultiScr,List<PerPieEntry> list,Context context,int flag,boolean check,int type,String date) {
        this.context=context;
        this.list=list;
        this.isMultiScr = isMultiScr;
        this.flag=flag;
        this.check=check;
        this.type=type;
        this.date=date;
    }

    @Override
    public int getCount() {
        Log.i("检测","size"+list.size());
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        linearLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_child, null);
        pieChart = linearLayout.findViewById(R.id.piechart);
        entries = (ArrayList<PieEntry>) list.get(position).getList();
        if(check){
            MyAsyncTask myAsyncTask = new MyAsyncTask(position,context,linearLayout,container,pieChart,1,list.size(),type,date);
            myAsyncTask.execute();
        }else{
            entries = new ArrayList<>();
            PieEntry pieEntry = new PieEntry(1, "?", context.getResources().getDrawable(R.drawable.moon));
            entries.add(pieEntry);
            colors.add(context.getResources().getColor(R.color.white));
            new InitPieChart(pieChart, entries, context, linearLayout.findViewById(R.id.add), linearLayout.findViewById(R.id.change), linearLayout.findViewById(R.id.data),
                    linearLayout.findViewById(R.id.linear), position, 0, colors,false,list.size(),type);
            container.addView(linearLayout);
        }
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }



}
