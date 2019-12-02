package com.example.catchtime.backgroundService;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.xdandroid.hellodaemon.AbsWorkService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class LocationService extends AbsWorkService {
    private String LocationName;
    private BDLocation perbdlocation;
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private int stepingsec=0;//运动的秒数
    private LatLng centerLatLng;
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return null;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        mMapView=new MapView(getApplicationContext());
        mBaiduMap=mMapView.getMap();
        locationClient = new LocationClient(getApplicationContext());
        mBaiduMap.setMyLocationEnabled(true);
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setScanSpan(5000);//每五秒进行一次扫描
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClient.setLocOption(locationClientOption);
        locationClient.start();
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String addr = bdLocation.getAddrStr();
                double lat = bdLocation.getLatitude();
                double lng = bdLocation.getLongitude();
                List<Poi> pois = bdLocation.getPoiList();
                for (Poi p:pois){
                    String name = p.getName();
                    String pAddr = p.getAddr();;
                }
                String time = bdLocation.getTime();
                double distance=distance(bdLocation.getLatitude(),bdLocation.getLongitude(),perbdlocation.getLatitude(),perbdlocation.getLongitude());
                if (distance>=2){//正常人的步行速度大概为1米每秒
                    stepingsec+=5;
                }else{;
                    stepingsec=0;
                }
                
                perbdlocation=bdLocation;
            }
        });
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {

    }

    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {

    }


    double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        double miles = dist * 60 * 1.1515;
        return miles;
    }
    //将角度转换为弧度
    static double deg2rad(double degree)
    {
        return degree / 180 * Math.PI;
    }
    //将弧度转换为角度
    static double rad2deg(double radian)
    {
        return radian * 180 / Math.PI;
    }

    /**
     * 返回是否在打卡范围内
     *
     * @return 返回值
     *var0表示圆心的坐标，var1代表圆心的半径，var2代表要判断的点是否在圆内
     *isCircleContainsPoint(LatLng var0, int var1, LatLng var2);
     */
    private boolean isRange(double lat1, double lon1, double lat2, double lon2) {
        return SpatialRelationUtil.isCircleContainsPoint(new LatLng(lat1, lon1), 100,new LatLng(lat2,lon2));
    }

}
