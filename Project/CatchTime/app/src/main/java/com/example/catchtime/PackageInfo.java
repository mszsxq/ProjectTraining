package com.example.catchtime;

/**
 * 统计数据---记录每个应用的包名，使用时长
 *

 */

public class PackageInfo {

    private long     mUsedTime;
    private String mPackageName;

    public PackageInfo( long mUsedTime, String mPackageName) {
        this.mUsedTime = mUsedTime;
        this.mPackageName = mPackageName;
    }



    public long getmUsedTime() {
        return mUsedTime;
    }

    public void setmUsedTime(long mUsedTime) {
        this.mUsedTime = mUsedTime;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    @Override
    public boolean equals(Object o) {
        //return super.equals(o);
        if(o == null) return false;
        if(this == o) return true;
        PackageInfo standardDetail = (PackageInfo)o;
        if( standardDetail.getmPackageName().equals(this.mPackageName) ){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        return (mPackageName + mUsedTime).hashCode();
    }
}
