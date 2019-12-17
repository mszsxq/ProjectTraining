package com.example.catchtime;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.example.catchtime.backgroundService.MyService;
import com.xdandroid.hellodaemon.DaemonEnv;

public class MyApp extends Application {
    @Override
    public void onCreate() {
//        SDKInitializer.initialize(getBaseContext());
        super.onCreate();
        DaemonEnv.initialize(this, MyService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
//        DaemonEnv.startServiceMayBind(MyService.class);
        MyService.sShouldStopService = false;
    }

    private static String myState;
    public String getState() {
        return myState;
    }
    public void setState(String s) {
        myState = s;
    }
}
