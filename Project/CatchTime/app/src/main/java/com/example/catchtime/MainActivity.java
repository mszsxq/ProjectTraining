package com.example.catchtime;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.catchtime.backgroundService.MyService;
import com.example.catchtime.backgroundService.Setting;
import com.example.catchtime.backgroundService.wakeup.KeepAliveHandler;
import com.example.catchtime.backgroundService.wakeup.WakeupAlarm;
import com.example.catchtime.backgroundService.wakeup.WakeupJobService;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.Contact;
import com.example.catchtime.entity.Location;
import com.example.catchtime.fragment.AccountFragment;
import com.example.catchtime.fragment.ActivitiesFragment;
import com.example.catchtime.fragment.LocationsFragment;
import com.example.catchtime.fragment.SettingFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.xdandroid.hellodaemon.IntentWrapper;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private FrameLayout mFrameLayout;
    private BottomBar bottomBar;
    private AccountFragment mAccountFragment;
    private ActivitiesFragment mActivitiesFragment;
    private LocationsFragment mLocationsFragment;
    private SettingFragment mSettingFragment;
    private Setting setting;
    private Handler handler;
    private Gson gson=new Gson();
    private List<Activity> activities;
    private List<Location> locations;
    private List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
       if (getSupportActionBar() != null){
           getSupportActionBar().hide();
       }

        intiView();
        initBottomBar();
        initFragment(0);

        Toast.makeText(this,"Starting service..",Toast.LENGTH_SHORT).show();
        setting=new Setting(this);
        setting.resetServiceWorkingTime();
        setting.resetStartTime();
        setting.setStartTime(System.currentTimeMillis());
//        startService(new Intent(this, MyService.class));
        DaemonEnv.startServiceMayBind(MyService.class);
        KeepAliveHandler.Companion.setJob(this);

//        Toast.makeText(this,"Starting service..",Toast.LENGTH_SHORT).show();
//        setting=new Setting(this);
//        setting.resetServiceWorkingTime();
//        setting.resetStartTime();
//        setting.setStartTime(System.currentTimeMillis());
//        startService(new Intent(this, MyService.class));
//        KeepAliveHandler.Companion.setJob(this);



        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    EventBus.getDefault().post(new String("ACTION_SCREEN_ON"));
                }
                else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    EventBus.getDefault().post(new String("ACTION_SCREEN_OFF"));
                }
                else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    EventBus.getDefault().post(new String("ACTION_USER_PRESENT"));
                }
                else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    EventBus.getDefault().post(new String("ACTION_CLOSE_SYSTEM_DIALOGS"));
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
        IntentWrapper.whiteListMatters(this, "轨迹跟踪服务的持续运行");

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                String s= (String) msg.obj;
                List<String> list= Arrays.asList(s.split("----"));

                activities=gson.fromJson(list.get(0),new TypeToken<List<Activity>>() {}.getType());
                locations=gson.fromJson(list.get(0),new TypeToken<List<Location>>() {}.getType());
                contacts=gson.fromJson(list.get(0),new TypeToken<List<Contact>>() {}.getType());
//                if (!isServiceRunning(getBaseContext(),"com.example.catchtime.backgroundService.MyService")){
//                if (!isServiceRunning(getBaseContext(),"MyService")){
                    Toast.makeText(getBaseContext(),"Starting service..",Toast.LENGTH_SHORT).show();
//                    DaemonEnv.startServiceMayBind(MyService.class);
//                    setting=new Setting(getApplicationContext());
//                    setting.resetServiceWorkingTime();
//                    setting.resetStartTime();
//                    setting.setStartTime(System.currentTimeMillis());
                    Intent intent1=new Intent(getBaseContext(), MyService.class);
                    intent1.putExtra("activities",list.get(0));
                    intent1.putExtra("locations",list.get(1));
                    intent1.putExtra("contacts",list.get(2));
                    startService(intent1);
//                    KeepAliveHandler.Companion.wakeUpTheService(getBaseContext());
//                    KeepAliveHandler.Companion.setJob(getApplicationContext());
//                    KeepAliveHandler.Companion.setJob(getBaseContext());
//                    Log.e("LocationService","执行KeepAliveHandler");
//                    WakeupJobService.Companion.setJob(getBaseContext());
//                    Log.e("LocationService","执行WakeupJobService");
//                    WakeupAlarm.Companion.setJob(getBaseContext());
//                    Log.e("LocationService","执行WakeupAlarm");


//                }
                return false;
            }
        });
        getData();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                    int id=sharedPreferences.getInt("user_id",-1);
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/BackgroundServlet?userid="+id);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("wer", "df" + info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
//        Intent intent1=new Intent(getBaseContext(),MyService.class);
//        startService(intent1);
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
