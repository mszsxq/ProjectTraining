package com.example.catchtime.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.DirectionalViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.catchtime.ChartPie.ChartPieBean;
import com.example.catchtime.ChartPie.SingleViewAdapter;
import com.example.catchtime.ChartPie.ViewAdapter;
import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private List<ChartPieBean> pieBeanList;
    private LinearLayout lineLayoutList;
    private Button buttonDay;
    private Button buttonWeek;
    private Button buttonMonth;
    private Button buttonYear;
    private ImageButton left;
    private ImageButton right;
    private View childAt;
    private ButtonClickListener listener;
    private DirectionalViewPager viewPager;
    private int[] chartPie = new int[7];
    private View root;

    private  ViewAdapter viewAdapter;
    private SingleViewAdapter singleViewAdapter;
    private ViewGroup mView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        return super.onCreateView(inflater, container, savedInstanceState);
        initData();
        Log.e("test", "初始化第0个页面");
        //扇形图
        childAt = inflater.inflate(R.layout.item_chart_pie, container, false);
        root =inflater.inflate(R.layout.accountfragment,container,false);
        getViews();
        registerViews();
        setChartPieData();
        showPager();
        //ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        //chartPie.setData(pieBeanList).start();
        // chartPie.start();
        return childAt;
    }

    private void registerViews() {
        buttonYear.setOnClickListener(listener);
        buttonWeek.setOnClickListener(listener);
        buttonMonth.setOnClickListener(listener);
        buttonDay.setOnClickListener(listener);
        left.setOnClickListener(listener);
        right.setOnClickListener(listener);
    }

    private void getViews() {
        buttonDay = childAt.findViewById(R.id.button_day);
        buttonWeek = childAt.findViewById(R.id.button_week);
        buttonMonth = childAt.findViewById(R.id.button_month);
        buttonYear = childAt.findViewById(R.id.button_year);
        listener = new ButtonClickListener();
        left=childAt.findViewById(R.id.chartpie_header_left);
        right = childAt.findViewById(R.id.chartpir_header_right);
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Drawable defDrawable = getResources().getDrawable(R.drawable.bt_shape_default);
            Drawable drawable = getResources().getDrawable(R.drawable.bt_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                switch (view.getId()) {
                    case R.id.button_day:
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);
                        break;
                    case R.id.button_week:
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonDay.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);
                       showSinglePager();
                        Log.i("周","zhou");
                        break;
                    case R.id.button_month:
                        view.setBackground(drawable);
                        buttonDay.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);
                        showSinglePager();
                        break;
                    case R.id.button_year:
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonDay.setBackground(defDrawable);
                        showSinglePager();
                        break;
                    case R.id.chartpie_header_left:
                        int position=viewPager.getCurrentItem();
                        if(position!=0){
                            viewPager.setCurrentItem(position-1);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                        Log.i("左边",position+"");
                        break;
                    case R.id.chartpir_header_right:
                        int rposition=viewPager.getCurrentItem();
                        if(rposition!=6){
                            viewPager.setCurrentItem(rposition+1);
                        }else{
                            viewPager.setCurrentItem(6);
                        }
                        Log.i("右边",rposition+"");
                        break;
                }
            }

        }


    }


    private void initData() {
        pieBeanList = new ArrayList<>();
        pieBeanList.add(new ChartPieBean(3090, "运动", R.color.main_green, R.drawable.run));
        pieBeanList.add(new ChartPieBean(501f, "娱乐", R.color.blue_rgba_24_261_255, R.drawable.yule));
        pieBeanList.add(new ChartPieBean(800, "睡觉", R.color.orange, R.drawable.moon));
        pieBeanList.add(new ChartPieBean(1000, "学校", R.color.red_2, R.drawable.school));
        pieBeanList.add(new ChartPieBean(2300, "路上", R.color.progress_color_default, R.drawable.load));
    }

    private void setChartPieData() {
        chartPie[0] = R.drawable.flower;
        chartPie[1] = R.drawable.flower2;
        chartPie[2] = R.drawable.star;


    }
    private void showSinglePager(){
        viewPager.setAdapter(singleViewAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    });
}
    private void showPager(){

        viewPager = (DirectionalViewPager) childAt.findViewById(R.id.pager);
        singleViewAdapter = new SingleViewAdapter(getFragmentManager());
        viewAdapter = new ViewAdapter(getFragmentManager());
        viewPager.setAdapter(viewAdapter);
        viewPager.setOrientation(DirectionalViewPager.VERTICAL);

       viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void showChartPie() {
        PieChart chart = root.findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieEntry pieEntry = new PieEntry((float) ((Math.random() * 5) + 5 / 5), " 测试", getResources().getDrawable(R.drawable.marker2));
            entries.add(pieEntry);
        }
        new InitPieChart(chart, entries, this.getContext());
    }

}
