package com.example.catchtime;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.catchtime.backgroundService.MyService;
import com.example.catchtime.backgroundService.Setting;
import com.example.catchtime.backgroundService.wakeup.KeepAliveHandler;
import com.example.catchtime.fragment.AccountFragment;
import com.example.catchtime.fragment.ActivitiesFragment;
import com.example.catchtime.fragment.LocationsFragment;
import com.example.catchtime.fragment.SettingFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.xdandroid.hellodaemon.IntentWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private FrameLayout mFrameLayout;
    private BottomBar bottomBar;
    private AccountFragment mAccountFragment;
    private ActivitiesFragment mActivitiesFragment;
    private LocationsFragment mLocationsFragment;
    private SettingFragment mSettingFragment;
    private Setting setting;
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
//        if (!isServiceRunning(this,"com.example.cakeshop.backgroundservice.MyService")){
//            Toast.makeText(this,"Starting service..",Toast.LENGTH_SHORT).show();
//            setting=new Setting(this);
//            setting.resetServiceWorkingTime();
//            setting.resetStartTime();
//            setting.setStartTime(System.currentTimeMillis());
//            startService(new Intent(this, MyService.class));
////        mKeepAliveHandler=new KeepAliveHandler();
//            KeepAliveHandler.Companion.setJob(this);
////        mKeepAliveHandler.setJob(this);
//        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
//                    Log.e("LocationService", "screen on");
                    EventBus.getDefault().post(new String("ACTION_SCREEN_ON"));

                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
//                    Log.e("LocationService", "screen off");
                    EventBus.getDefault().post(new String("ACTION_SCREEN_OFF"));

                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
//                    Log.e("LocationService", "user present");
                    EventBus.getDefault().post(new String("ACTION_USER_PRESENT"));

                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
//                    Log.e("LocationService", "close system dialogs");
                    EventBus.getDefault().post(new String("ACTION_CLOSE_SYSTEM_DIALOGS"));
                }

            }
        };

        registerReceiver(mBatInfoReceiver, filter);
        IntentWrapper.whiteListMatters(this, "轨迹跟踪服务的持续运行");

    }
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
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
