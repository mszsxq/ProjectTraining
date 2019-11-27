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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

//import android.support.v4.view.PagerAdapter;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private int flag=0;
    private Context context;
    public UltraPagerAdapter(boolean isMultiScr) {
        this.isMultiScr = isMultiScr;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        PieChart pieChart=linearLayout.findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            PieEntry pieEntry=new PieEntry((float) ((Math.random() * 5) + 5 / 5),"测试");
//            PieEntry pieEntry=new PieEntry((float) ((Math.random() * 5) + 5 / 5),"测试",context.getResources().getDrawable(R.drawable.marker2));
            entries.add(pieEntry);
        }

        new InitPieChart(pieChart,entries,context,linearLayout.findViewById(R.id.add),linearLayout.findViewById(R.id.change),linearLayout.findViewById(R.id.data),
                linearLayout.findViewById(R.id.linear));
        switch (position) {
            case 0:
//                linearLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                flag=0;
                break;
            case 1:
//                linearLayout.setBackgroundColor(Color.parseColor("#673AB7"));
                flag=1;
                break;
            case 2:
//                linearLayout.setBackgroundColor(Color.parseColor("#009688"));
                flag=2;
                break;
            case 3:
//                linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
                flag=3;
                break;
            case 4:
//                linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
                flag=4;
                break;
            case 5:
//                linearLayout.setBackgroundColor(Color.parseColor("#009688"));
                flag=5;
                break;
            case 6:
//                linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
                flag=6;
                break;
        }
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
