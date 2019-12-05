package com.example.catchtime.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.DirectionalViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catchtime.ChartPie.ChartPieBean;
import com.example.catchtime.ChartPie.SingleViewAdapter;
import com.example.catchtime.ChartPie.ViewAdapter;
import com.example.catchtime.R;
import com.example.catchtime.chart.PerPieEntry;
import com.example.catchtime.entity.User;
import com.example.catchtime.entity.chartData;
import com.example.catchtime.fragment.UltraPagerAdapter;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.tmall.ultraviewpager.UltraViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
public class AccountFragment extends Fragment {
   private int num=0;
    private List<ChartPieBean> pieBeanList;
    private LinearLayout lineLayoutList;
    private TextView datee;
    private Button buttonDay;
    private Button buttonWeek;
    private Button buttonMonth;
    private Button buttonYear;
    private ImageButton left;
    private ImageButton right;
    private View childAt;
    private DirectionalViewPager viewPager;
    private View root;
    private  ViewAdapter viewAdapter;
    private SingleViewAdapter singleViewAdapter;
    private ViewGroup mView;
    private UltraViewPager ultraViewPager;
    private PagerAdapter adapter;
    private ArrayList<PerPieEntry> list;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            num=Integer.valueOf(object);
            Log.i("检测","num"+num);
            list=new ArrayList<>();
            int flag=0;
            while(flag<num){
                list.add(new PerPieEntry());
                flag++;
                Log.i("检测","num"+num);
            }
            adapter = new UltraPagerAdapter(false,list,getContext(),0,true,0,"");
            ultraViewPager.setAdapter(adapter);
            ultraViewPager.setCurrentItem(num-1);
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //扇形图
        Log.e("test","初始化第0个页面");
        Window window=this.getActivity().getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        childAt = inflater.inflate(R.layout.item_chart_pie, container,false);
        ultraViewPager=childAt.findViewById(R.id.ultra_viewpager);
        ultraViewPager.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        getViews();
        toServer(1);
        return childAt;
    }
    private void getViews() {
        ButtonClickListener buttonClickListener=new ButtonClickListener();
        buttonDay = childAt.findViewById(R.id.button_day);
        buttonWeek = childAt.findViewById(R.id.button_week);
        buttonMonth = childAt.findViewById(R.id.button_month);
        buttonYear = childAt.findViewById(R.id.button_year);
        datee=childAt.findViewById(R.id.chartpie_header_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dat = sdf.format(new Date());
        datee.setText(dat);
        left=childAt.findViewById(R.id.chartpie_header_left);
        right = childAt.findViewById(R.id.chartpir_header_right);
        buttonDay.setOnClickListener(buttonClickListener);
        buttonMonth.setOnClickListener(buttonClickListener);
        buttonWeek.setOnClickListener(buttonClickListener);
        buttonYear.setOnClickListener(buttonClickListener);
        right.setOnClickListener(buttonClickListener);
        left.setOnClickListener(buttonClickListener);
        RelativeLayout relativeLayout=childAt.findViewById(R.id.chartpie_choose);
        relativeLayout.setOnClickListener(new ButtonClickListener());
    }
    class ButtonClickListener implements View.OnClickListener {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        //日周年月的切换由改变适配器来实现
        @Override
        public void onClick(View view) {
            Log.e("点击","时间");
            System.out.println("anniu dianji点击");
            Drawable defDrawable = getResources().getDrawable(R.drawable.bt_shape_default);
            Drawable drawable = getResources().getDrawable(R.drawable.bt_shape);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                switch (view.getId()) {
                    case R.id.button_day:
                        Log.e("按钮","day");
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);

                        String dd = sdf.format(new Date());
                        datee.setText(dd);
                        ultraViewPager.setCurrentItem(num-1);
//                        List<PieEntry> entryList;
                        ArrayList<PerPieEntry> list=new ArrayList<>();
                        int flag=0;
                        while(flag<num){
                            list.add(new PerPieEntry());
                            flag++;
                        }
                        adapter = new UltraPagerAdapter(false,list,getContext(),0,true,0,"");
                        ultraViewPager.setAdapter(adapter);
                        ultraViewPager.setCurrentItem(num-1);
                        break;
                    case R.id.button_week:
                        Log.e("按钮","week");
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonDay.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);
                        if(num<=7){
                            ArrayList<PerPieEntry> list1=new ArrayList<>();
                            list1.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list1,getContext(),1,false,1,"");
                            ultraViewPager.setAdapter(adapter);
                        }else{
                            int a = num%7;
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(new Date());
                            calendar.add(calendar.DATE,-a);
                            String date2= sdf.format(calendar.getTime());
                            ArrayList<PerPieEntry> list1=new ArrayList<>();
                            list1.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list1,getContext(),1,true,1,date2);
                            ultraViewPager.setAdapter(adapter);
                        }

                        break;
                    case R.id.button_month:
                        Log.e("按钮","month");
                        view.setBackground(drawable);
                        buttonDay.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonYear.setBackground(defDrawable);
                        if(num<=30){
                            ArrayList<PerPieEntry> list2=new ArrayList<>();
                            list2.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list2,getContext(),2,false,2,"");
                            ultraViewPager.setAdapter(adapter);
                        }else{
                            int a = num%30;
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(new Date());
                            calendar.add(calendar.DATE,-a);
                            String date2= sdf.format(calendar.getTime());
                            ArrayList<PerPieEntry> list2=new ArrayList<>();
                            list2.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list2,getContext(),2,true,2,date2);
                            ultraViewPager.setAdapter(adapter);
                        }

                        break;
                    case R.id.button_year:
                        Log.e("按钮","year");
                        view.setBackground(drawable);
                        buttonMonth.setBackground(defDrawable);
                        buttonWeek.setBackground(defDrawable);
                        buttonDay.setBackground(defDrawable);
                        if(num<=365){
                            ArrayList<PerPieEntry> list3=new ArrayList<>();
                            list3.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list3,getContext(),3,false,3,"");
                            ultraViewPager.setAdapter(adapter);
                        }else{
                            int a = num%365;
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(new Date());
                            calendar.add(calendar.DATE,-a);
                            String date2= sdf.format(calendar.getTime());
                            ArrayList<PerPieEntry> list3=new ArrayList<>();
                            list3.add(new PerPieEntry());
                            adapter = new UltraPagerAdapter(false,list3,getContext(),3,true,3,date2);
                            ultraViewPager.setAdapter(adapter);
                        }

                        break;
                    case R.id.chartpie_header_left:
                        int position=ultraViewPager.getCurrentItem();
                        if(position!=0){
                            ultraViewPager.setCurrentItem(position-1);
                            Date date=null;
                            try {
                                date = sdf.parse(datee.getText().toString().trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(date);
                            calendar.add(calendar.DATE,-1);
                            String date2= sdf.format(calendar.getTime());
                            datee.setText(date2);
                        }else{
                            ultraViewPager.setCurrentItem(0);
                        }
                        Log.i("左边",position+"");
                        break;
                    case R.id.chartpir_header_right:
                        int rposition=ultraViewPager.getCurrentItem();
                        if(rposition!=num-1){
                            ultraViewPager.setCurrentItem(rposition+1);
                            Date date=null;
                            try {
                                date = sdf.parse(datee.getText().toString().trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(date);
                            calendar.add(calendar.DATE,1);
                            String date3= sdf.format(calendar.getTime());
                            datee.setText(date3);
                        }else{
                            ultraViewPager.setCurrentItem(num-1);
                        }
                        Log.i("右边",rposition+"");
                        break;
                }
            }

        }


    }
    private void toServer(int user_id) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.42.184:8080/Catchtime/ChartDate?user_id="+user_id+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
                   wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String num){
        Message msg = Message.obtain();
        msg.obj =num;
        handler.sendMessage(msg);
    }

//    private void initData() {
//        pieBeanList = new ArrayList<>();
//        pieBeanList.add(new ChartPieBean(3090, "运动", R.color.main_green, R.drawable.run));
//        pieBeanList.add(new ChartPieBean(501f, "娱乐", R.color.blue_rgba_24_261_255, R.drawable.yule));
//        pieBeanList.add(new ChartPieBean(800, "睡觉", R.color.orange, R.drawable.moon));
//        pieBeanList.add(new ChartPieBean(1000, "学校", R.color.red_2, R.drawable.school));
//        pieBeanList.add(new ChartPieBean(2300, "路上", R.color.progress_color_default, R.drawable.load));
//    }
//
//    private void setChartPieData() {
//        chartPie[0] = R.drawable.flower;
//        chartPie[1] = R.drawable.flower2;
//        chartPie[2] = R.drawable.star;
//
//
//    }
//    private void showSinglePager(){
//        viewPager.setAdapter(singleViewAdapter);
//        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//        @Override
//        public void onPageSelected(int position) {
//            // TODO Auto-generated method stub
//            viewPager.setCurrentItem(position);
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//}
//    private void showPager(){
//
//        viewPager = (DirectionalViewPager) childAt.findViewById(R.id.pager);
//        singleViewAdapter = new SingleViewAdapter(getFragmentManager());
//        viewAdapter = new ViewAdapter(getFragmentManager());
//        viewPager.setAdapter(viewAdapter);
//        viewPager.setOrientation(DirectionalViewPager.VERTICAL);
//
//       viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                // TODO Auto-generated method stub
//                viewPager.setCurrentItem(position);
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//    }
//
//    private void showChartPie() {
//        PieChart chart = root.findViewById(R.id.piechart);
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            PieEntry pieEntry = new PieEntry((float) ((Math.random() * 5) + 5 / 5), " 测试", getResources().getDrawable(R.drawable.marker2));
//            entries.add(pieEntry);
//        }
//        new InitPieChart(chart, entries, this.getContext());
//    }


}



