package com.example.catchtime;

import android.app.usage.UsageEvents;

import java.util.ArrayList;

/**
 * 记录打开一次应用的时候，其中的详情，包括这次打开的应用名，使用时长，以及打开了哪些activity，及各个activity的使用详情
 *
 * Created by Wingbu on 2017/7/18.
 */

public class OneTimeDetails {
    private String pkgName;
    private long                          useTime;
    private ArrayList<UsageEvents.Event> OneTimeDetailEventList;

    public OneTimeDetails(String pkg, long useTime, ArrayList<UsageEvents.Event> oneTimeDetailList) {
        this.pkgName = pkg;
        this.useTime = useTime;
        OneTimeDetailEventList = oneTimeDetailList;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }


}
