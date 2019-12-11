package com.example.catchtime.backgroundService;

import android.app.Application;

import com.xdandroid.hellodaemon.DaemonEnv;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaemonEnv.initialize(this, MyService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        MyService.sShouldStopService = false;
    }

    private static String myState;
    public String getState() {
        return myState;
    }
    public void setState(String s) {
        myState = s;
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
}
