package com.example.catchtime.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.catchtime.ChartPie.ChartPie;
import com.example.catchtime.ChartPie.ChartPieBean;
import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.activity.AddActivityDetial;
import com.roughike.bottombar.BottomBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private List<ChartPieBean> pieBeanList;
    private LinearLayout lineLayoutList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        initData();
        Log.e("test","初始化第0个页面");
        //底部的曲线图
        Window window=this.getActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        View childAt = inflater.inflate(R.layout.item_chart_pie, container,false);
        //lineLayoutList.addView(childAt);
        ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        chartPie.setData(pieBeanList).start();
        chartPie.start();
        return childAt;
    }

    protected void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.accountfragment,null);
        lineLayoutList = view.findViewById(R.id.line_layout_list);
        initData();
        drawPie();
    }
    private void initData() {
        pieBeanList = new ArrayList<>();
        pieBeanList.add(new ChartPieBean(3090, "运动", R.color.main_green,R.drawable.run));
        pieBeanList.add(new ChartPieBean(501f, "娱乐", R.color.blue_rgba_24_261_255,R.drawable.yule));
        pieBeanList.add(new ChartPieBean(800, "睡觉", R.color.orange,R.drawable.moon));
        pieBeanList.add(new ChartPieBean(1000, "学校", R.color.red_2,R.drawable.school));
        pieBeanList.add(new ChartPieBean(2300, "路上", R.color.progress_color_default,R.drawable.load));
    }

    private void drawPie() {
        //底部的曲线图
        View childAt = View.inflate(getContext(), R.layout.item_chart_pie, null);
        //lineLayoutList.addView(childAt);
        ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        chartPie.setData(pieBeanList).start();
        chartPie.start();
    }
}
