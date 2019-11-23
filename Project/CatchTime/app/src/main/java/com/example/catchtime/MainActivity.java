package com.example.catchtime;

<<<<<<< HEAD
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;


=======
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
>>>>>>> 4c5206f1109f829c21c808abe7b6a29cb0e729c5
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

<<<<<<< HEAD
import com.example.catchtime.ChartPie.ChartPie;
import com.example.catchtime.ChartPie.ChartPieBean;


import java.util.ArrayList;
import java.util.List;

=======
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.catchtime.fragment.AccountFragment;
import com.example.catchtime.fragment.ActivitiesFragment;
import com.example.catchtime.fragment.LocationsFragment;
import com.example.catchtime.fragment.SettingFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
>>>>>>> 4c5206f1109f829c21c808abe7b6a29cb0e729c5

public class MainActivity extends AppCompatActivity {
    private List<ChartPieBean> pieBeanList;
    private LinearLayout lineLayoutList;

    private FrameLayout mFrameLayout;
    private BottomBar bottomBar;
    private AccountFragment mAccountFragment;
    private ActivitiesFragment mActivitiesFragment;
    private LocationsFragment mLocationsFragment;
    private SettingFragment mSettingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        initView();
    }

    protected void initView() {
        lineLayoutList = findViewById(R.id.line_layout_list);
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
        View childAt = View.inflate(this, R.layout.item_chart_pie, null);
        lineLayoutList.addView(childAt);
        ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        chartPie.setData(pieBeanList).start();
        chartPie.start();
    }

=======
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        intiView();
        initBottomBar();
        initFragment(0);

    }

    private void initFragment(@Nullable int i) {
        //初始化fragment

        //初始化fragmentmanager组件
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragments(transaction);

        switch (i){
            case 0:
                Log.e("test","显示第0个页面");
                hideFragments(transaction);
                if (mAccountFragment==null){
                    mAccountFragment=new AccountFragment();
                    transaction.add(R.id.contentContainer,mAccountFragment,"account");
                }else {
                    transaction.show(mAccountFragment);
                }
                break;
            case 1:
                Log.e("test","显示第1个页面");
                hideFragments(transaction);
                if (mActivitiesFragment ==null){
                    mActivitiesFragment =new ActivitiesFragment();
                    transaction.add(R.id.contentContainer,mActivitiesFragment,"activities");
                }else {
                    transaction.show(mActivitiesFragment);
                }
                break;
            case 2:
                Log.e("test","显示第2个页面");
                hideFragments(transaction);
                if (mLocationsFragment ==null){
                    mLocationsFragment =new LocationsFragment();
                    transaction.add(R.id.contentContainer,mLocationsFragment,"locations");
                }else {
                    transaction.show(mLocationsFragment);
                }
                break;
            case 3:
                Log.e("test","显示第3个页面");
                hideFragments(transaction);
                if (mSettingFragment ==null){
                    mSettingFragment =new SettingFragment();
                    transaction.add(R.id.contentContainer,mSettingFragment,"setting");
                }else {
                    transaction.show(mSettingFragment);
                }
                break;
                default:
                    break;
        }
        //

        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mAccountFragment != null) {
            transaction.hide(mAccountFragment);
        }
        if (mActivitiesFragment != null) {
            transaction.hide(mActivitiesFragment);
        }
        if (mLocationsFragment != null) {
            transaction.hide(mLocationsFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
    }

    private void initBottomBar() {

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {

            }
        });
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected( int tabId) {
                Log.e("test","id   "+tabId);
                switch (tabId){
                    case R.id.bb_menu_account:
                        initFragment(0);
                        Log.e("test","0");
                    break;
                    case R.id.bb_menu_activity:
                        initFragment(1);
                        Log.e("test","1");
                    break;
                    case R.id.bb_menu_location:
                        initFragment(2);
                        Log.e("test","2");
                    break;
                    case R.id.bb_menu_setting:
                        initFragment(3);
                        Log.e("test","3");
                    break;

                }
            }
        });
    }

    private void intiView() {
        bottomBar=findViewById(R.id.bottomBar);
        mFrameLayout=findViewById(R.id.contentContainer);
    }
>>>>>>> 4c5206f1109f829c21c808abe7b6a29cb0e729c5

}
