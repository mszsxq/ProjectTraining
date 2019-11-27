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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

//import android.support.v4.view.PagerAdapter;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private int flag=0;
    private Context context;
    private List<PerPieEntry> list;
    public UltraPagerAdapter(boolean isMultiScr,List<PerPieEntry> list,Context context,int flag) {
        this.context=context;
        this.list=list;
        this.isMultiScr = isMultiScr;
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        PieChart pieChart = linearLayout.findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries = (ArrayList<PieEntry>) list.get(position).getList();
        if (entries == null) {
            entries=new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                PieEntry pieEntry = new PieEntry((float) ((Math.random() * 5) + 5 / 5), "测试", context.getResources().getDrawable(R.drawable.marker2));
                entries.add(pieEntry);
            }
        }
        new InitPieChart(pieChart, entries, container.getContext(), linearLayout.findViewById(R.id.add), linearLayout.findViewById(R.id.change), linearLayout.findViewById(R.id.data),
                linearLayout.findViewById(R.id.linear),position,flag);
        container.addView(linearLayout);
//        Button button=linearLayout.findViewById(R.id.add);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("按钮","点击了第"+flag+"天的选中");
//                Intent intent=new Intent(context, ModifyPage.class);
//                context.startActivity(intent);
//            }
//        });
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
