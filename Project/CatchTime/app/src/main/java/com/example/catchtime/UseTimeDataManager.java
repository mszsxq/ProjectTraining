package com.example.catchtime;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class UseTimeDataManager {
    public static final String TAG = "Wingbu";
    private static UseTimeDataManager mUseTimeDataManager;
    public static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private Context mContext;
    //记录从系统中读取的数据
    private ArrayList<UsageEvents.Event> mEventList;
    private ArrayList<UsageEvents.Event> mEventListChecked;
    private ArrayList<UsageStats> mStatsList;

    //记录打开一次应用，使用的activity详情
    private ArrayList<OneTimeDetails> mOneTimeDetailList = new ArrayList<>();
    //主界面数据
    private ArrayList<PackageInfo> mPackageInfoList = new ArrayList<>();
    public UseTimeDataManager(Context context) {
        this.mContext = context;
    }

    public static UseTimeDataManager getInstance(Context context){
        if(mUseTimeDataManager == null){
            mUseTimeDataManager = new UseTimeDataManager(context);
        }
        return mUseTimeDataManager;
    }


    public int refreshData(){
        mEventList = getEventList();
        mStatsList = getUsageList();
        //获取数据之后，进行数据的处理
        mEventListChecked = getEventListChecked();

        refreshOneTimeDetailList(0);
        refreshPackageInfoList();

        return 0;
    }

    //分类完成，初始化主界面所用到的数据
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void refreshPackageInfoList() {
        mPackageInfoList.clear();
        for (int i = 0 ; i < mStatsList.size() ; i++){
            PackageInfo info = new PackageInfo(calculateUseTime(mStatsList.get(i).getPackageName()),mStatsList.get(i).getPackageName());
            mPackageInfoList.add(info);
        }
    }

    //按照使用时间的长短进行排序，获取应用使用情况列表
    public ArrayList<PackageInfo> getmPackageInfoListOrderByTime() {

        for (int n = 0 ; n < mPackageInfoList.size() ; n++){
            for (int m = n+1 ; m < mPackageInfoList.size() ; m++){
                if(mPackageInfoList.get(n).getmUsedTime() < mPackageInfoList.get(m).getmUsedTime()){
                    PackageInfo temp = mPackageInfoList.get(n);
                    mPackageInfoList.set(n,mPackageInfoList.get(m));
                    mPackageInfoList.set(m,temp);
                }
            }
        }
        return mPackageInfoList;
    }





    //从系统中获取event数据
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private ArrayList<UsageEvents.Event> getEventList(){
        long endTime = 0, startTime = 0,currentStamp = 0;
        endTime = Calendar.getInstance().getTimeInMillis();
        //前一天0点
        currentStamp -= currentStamp % DAY_IN_MILLIS;
        //前一天11点
        startTime =currentStamp-1000*60*60;
        ArrayList<UsageEvents.Event> mEventList = new ArrayList<>();
        UsageStatsManager mUsmManager = (UsageStatsManager) mContext.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = mUsmManager.queryEvents(startTime, endTime);
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);
            if (e != null && (e.getEventType() == 1 || e.getEventType() == 2)){
                mEventList.add(e);
            }
        }

        return mEventList;
    }

    //从系统中获取Usage数据
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private ArrayList<UsageStats> getUsageList() {
        long endTime = 0, startTime = 0,currentStamp = 0;
        endTime = Calendar.getInstance().getTimeInMillis();
        //前一天0点
        currentStamp -= currentStamp % DAY_IN_MILLIS;
        //前一天11点
        startTime =currentStamp-1000*60*60;
        ArrayList<UsageStats> list1 = new ArrayList<>();
        UsageStatsManager mUsmManager = (UsageStatsManager)mContext.getSystemService(Context.USAGE_STATS_SERVICE);

        Map<String, UsageStats> map = mUsmManager.queryAndAggregateUsageStats(startTime,endTime);
        ArrayList<UsageStats> list =(ArrayList<UsageStats>)mUsmManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);

        for (Map.Entry<String, UsageStats> entry : map.entrySet()) {
            UsageStats stats = entry.getValue();
            if(stats.getTotalTimeInForeground() > 0){
                list1.add(stats);
            }
        }

//


        for (int i = 0; i < list.size()-1 ; i++) {
            for (int j = list.size()-1 ; j > i; j--) {
                if (list.get(j).getPackageName().equals(list.get(i).getPackageName()) ) {
                   list1.add(list.get(j));//把相同元素加入list(找出相同的)
                }
            }
        }
        list.removeAll(list1);
        ArrayList list2 =new ArrayList();
        for(UsageStats s:list){
            if (s.getTotalTimeInForeground()>1000*60*10){
                list2.add(s);
                Log.e("app",s.getPackageName());
            }
        }
        return list2;
//        return  list1;
    }
    //仅保留 event 中 type 为 1或者2 的
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private ArrayList<UsageEvents.Event> getEventListChecked(){
        ArrayList<UsageEvents.Event> mList = new ArrayList<>();
        for(int i = 0; i < mEventList.size() ; i++){
            if(mEventList.get(i).getEventType() == 1 ||(mEventList.get(i).getEventType() == 2)){
                mList.add(mEventList.get(i));
            }
        }
        return mList;
    }



    //从 startIndex 开始分类event  直至将event分完
    //每次从0开始，将原本的 mOneTimeDetailList 清除一次,然后开始分类
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void refreshOneTimeDetailList(int startIndex){

        if(startIndex == 0){
            Log.i(TAG,"  refreshOneTimeDetailList()     每次从0开始，将原本的 mOneTimeDetailList 清除一次,然后开始分类 " );
            if(mOneTimeDetailList != null){
                mOneTimeDetailList.clear();
            }
        }

        long totalTime = 0 ;
        int usedIndex = 0;
        String pkg = null;
        ArrayList<UsageEvents.Event> list = new ArrayList();
        for (int i = startIndex ; i < mEventListChecked.size() ; i++){
            if( i == startIndex ){
                if(mEventListChecked.get(i).getEventType() == 2){
                    Log.i(TAG,"  refreshOneTimeDetailList()     warning : 每次打开一个app  第一个activity的类型是 2     ");
                }
                pkg = mEventListChecked.get(i).getPackageName();
                list.add(mEventListChecked.get(i));
            }else {
                if(pkg != null ){
                    if(pkg.equals(mEventListChecked.get(i).getPackageName())){
                        list.add(mEventListChecked.get(i));
                        if( i == mEventListChecked.size()-1 ){
                            usedIndex = i ;
                        }
                    }else {
                        usedIndex = i;
                        break;
                    }
                }
            }
        }

        Log.i(TAG,"   mEventListChecked 分类:   before  check :   list.size() = " + list.size());
        checkEventList(list);
        Log.i(TAG,"   mEventListChecked 分类:   after  check :   list.size() = " + list.size());

      
        for(int i = 1 ; i < list.size() ; i += 2){
            if(list.get(i).getEventType() == 2 && list.get( i - 1).getEventType() == 1 ){
                totalTime += ( list.get(i).getTimeStamp() - list.get( i - 1).getTimeStamp());
            }
        }
        OneTimeDetails oneTimeDetails = new OneTimeDetails(pkg,totalTime,list);
        mOneTimeDetailList.add(oneTimeDetails);

        if(usedIndex < mEventListChecked.size() - 1){
            refreshOneTimeDetailList(usedIndex);
        }else {
            Log.i(TAG,"  refreshOneTimeDetailList()     已经将  mEventListChecked 分类完毕   ");
        }

    }



    // 采用回溯的思想：
    // 从头遍历EventList，如果发现异常数据，则删除该异常数据，并从头开始再次进行遍历，直至无异常数据
    // （异常数据是指：event 均为 type=1 和type=2 ，成对出现，一旦发现未成对出现的数据，即视为异常数据）
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void checkEventList(ArrayList<UsageEvents.Event> list){
        boolean isCheckAgain = false ;
        for (int i = 0 ; i < list.size() - 1 ; i += 2){
            if(list.get(i).getClassName().equals(list.get(i+1).getClassName())){
                if(list.get(i).getEventType() != 1){
                    Log.i(UseTimeDataManager.TAG,"   EventList 出错  ： "+list.get(i).getPackageName() +"  "+ DateUtils.formatSameDayTime(list.get(i).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString());
                    list.remove(i);
                    isCheckAgain = true;
                    break;
                }
                if(list.get(i+1).getEventType() != 2){
                    Log.i(UseTimeDataManager.TAG,"   EventList 出错 ： "+list.get(i+1).getPackageName() +"  "+ DateUtils.formatSameDayTime(list.get(i+1).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString());
                    list.remove(i);
                    isCheckAgain = true;
                    break;
                }
            }else {
                //i和i+1的className对不上，则删除第i个数据，重新检查
                list.remove(i);
                isCheckAgain = true;
                break;
            }
        }
        if(isCheckAgain){
            checkEventList(list);
        }
    }





    //根据event计算使用时间
    public long calculateUseTime(String pkg){
        long useTime = 0 ;
        for(int i = 0 ; i < mOneTimeDetailList.size() ; i++){
            if(mOneTimeDetailList.get(i).getPkgName().equals(pkg)){
                useTime += mOneTimeDetailList.get(i).getUseTime();
            }else {

            }
        }
        return useTime;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UsageStats getUsageStats(String pkg){
        for(int i = 0 ; i < mStatsList.size() ; i++){
            if(mStatsList.get(i).getPackageName().equals(pkg)){
                return mStatsList.get(i);
            }
        }
        return null;
    }
}
