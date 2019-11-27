package com.example.catchtime;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.catchtime.ChartPie.ChartPie;
import com.example.catchtime.ChartPie.ChartPieBean;


import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {


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

}
