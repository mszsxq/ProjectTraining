package com.example.catchtime;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class UseTimeAdapter extends BaseAdapter {

    private ArrayList<PackageInfo> mPackageInfoList;
    private PackageManager packageManager;
    private UseTimeDataManager mUseTimeDataManager;
    private int itemLayoutId;
    private Context context;

    public UseTimeAdapter(Context context, ArrayList<PackageInfo> PackageInfoList, int itemLayoutId) {
        this.mPackageInfoList = PackageInfoList;
        mUseTimeDataManager = UseTimeDataManager.getInstance(context);
        this.itemLayoutId = itemLayoutId;
        this.context=context;
        packageManager =context.getPackageManager();
    }








    @Override
    public int getCount() {
        return mPackageInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPackageInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        TextView tv_index = (TextView) convertView.findViewById(R.id.index);
        ImageView iv_icon = (ImageView) convertView.findViewById(R.id.app_icon);
        TextView tv_used_time = (TextView) convertView.findViewById(R.id.use_time);
        tv_index.setText("" + (position+1) );
        try {
            iv_icon.setImageDrawable(packageManager.getApplicationIcon(mPackageInfoList.get(position).getmPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_used_time.setText(" " + getTotalTimeFromUsage(mPackageInfoList.get(position).getmPackageName())/(1000*60
        )+"min");
        return convertView;
    }





    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private long getTotalTimeFromUsage(String pkg){
//        Log.e("appname",);
        UsageStats stats = mUseTimeDataManager.getUsageStats(pkg);
        if(stats == null){
            return 0;
        }
        return stats.getTotalTimeInForeground();
    }
}
