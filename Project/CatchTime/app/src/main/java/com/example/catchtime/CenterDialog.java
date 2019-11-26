package com.example.catchtime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class CenterDialog extends Dialog implements   View.OnClickListener{
    private OnCenterItemClickListener listener;
    private Context context;      // 上下文
    private int[] listenedItems;  // 要监听的控件id
    private MapView mapView;
    private LocationClient locationClient;
    //定位选项类
    private LocationClientOption locationClientOption;
    //百度地图控制器
    private BaiduMap baiduMap;


    @Override
    public void onClick(View view) {
        dismiss();//注意：我在这里加了这句话，表示只要按任何一个控件的id,弹窗都会消失，不管是确定还是取消。
        listener.OnCenterItemClick(this, view);
    }

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(CenterDialog dialog, View view);
    }
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    public CenterDialog(Context context, int[] listenedItems) {
        super(context); //dialog的样式
        this.context = context;
        this.listenedItems = listenedItems;
        SDKInitializer.initialize(context.getApplicationContext());
        setContentView(R.layout.newplacepopup);
        mapView = findViewById(R.id.mapView);
        mapView.onResume();
        baiduMap = mapView.getMap();
        //设置图层定位
        baiduMap.setMyLocationEnabled(true);
        hideLogo();
        zoomLevelOp();
        locationOption();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener((View.OnClickListener) this);
        }
    }
    private void locationOption() {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setScanSpan(1000);
        SDKInitializer.setCoordType(CoordType.GCJ02);
        locationClientOption.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy
        );
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClient.setLocOption(locationClientOption);
        locationClient.start();
        locationClient.registerLocationListener(
                new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        //获取定位详细数据
                        //获取地址信息
                        String addr = bdLocation.getAddrStr();
                        Log.i("lww", "地址：" + addr);
                        //获取经纬度
                        double lat = bdLocation.getLatitude();
                        double lng = bdLocation.getLongitude();
                        Log.i("lww", "纬度："+lat+";经度："+lng);
                        //获取周边POI信息
                        List<Poi> pois = bdLocation.getPoiList();
                        for (Poi p:pois) {
                            String name = p.getName();
                            String pAddr = p.getAddr();
                            Log.i("lww", "POI:" + name+":"+pAddr);
                        }
                        String time = bdLocation.getTime();
                        Log.i("lww", time);
                        //将定位数据显示在地图上
                        showLocOnMap(lat, lng);
                    }
                }
        );
    }

    private void showLocOnMap(double lat, double lng) {
        //  获取定位图标
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(R.drawable.waters);
        //设置显示方式
        MyLocationConfiguration config =
                new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.COMPASS,//罗盘态
                        false,//是否需要方向
                        icon
                );
        //应用显示方式
        baiduMap.setMyLocationConfiguration(config);
        LatLng latLng = new LatLng(
                lat + 0.00374531687912,
                lng + 0.008774687519);
        //显示
        MyLocationData locData = new MyLocationData
                .Builder()
                .latitude(latLng.latitude)
                .longitude(latLng.longitude)
                .build();
        baiduMap.setMyLocationData(locData);

        //移动到中心位置
        MapStatusUpdate msu =
                MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(msu);
    }
    private void hideLogo() {
        View child = mapView.getChildAt(1);
        if(null != child &&
                (child instanceof ImageView ||
                        child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
    }
    private void zoomLevelOp() {
        baiduMap.setMaxAndMinZoomLevel(19, 13);
        //设置默认比例为200m
        MapStatusUpdate msu = MapStatusUpdateFactory
                .zoomTo(16);
        baiduMap.setMapStatus(msu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!locationClient.isStarted()){
            locationClient.start();
        }
    }


}
