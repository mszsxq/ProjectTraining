package com.example.catchtime;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.daimajia.swipe.SwipeLayout;
import com.example.catchtime.location.ChangeLocal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ModifyPage extends SwipeBackActivity implements View.OnClickListener {
    private MapView mapView;
    private TextView view1;
    private TextView view2;
    private int hour, min;
    private Calendar cal;
    private UiSettings uiSettings;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private RelativeLayout localrelative;
    private TextView localtv;
    private List<String> localname;
    private Button buttonconf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.modifypage);
        mapView = (MapView) findViewById(R.id.bbmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        localrelative= (RelativeLayout) findViewById(R.id.localrelative);
        localtv= (TextView) findViewById(R.id.tvcontent);
        view1 = (TextView) findViewById(R.id.time_start);
        view2 = (TextView) findViewById(R.id.time_end);
        view1.setOnClickListener((View.OnClickListener) this);
        view2.setOnClickListener((View.OnClickListener) this);
        localrelative.setOnClickListener(this);
        buttonconf= (Button) findViewById(R.id.buttonconf);
        buttonconf.setOnClickListener(this);
        locationOption();
        initializeMap();
        hideLogo();
        zoomLevelOp();
        getbeginDate();
        getoverDate();
        localtv.setText("选择地点");
    }
    private void getoverDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_start:
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        if (hour < 10 && min < 10) {
                            view1.setText("0" + hour + ":" + "0" + min);
                        } else if (hour < 10) {
                            view1.setText("0" + hour + ":" + min);
                        } else if (min < 10) {
                            view1.setText(hour + ":" + "0" + min);
                        } else {
                            view1.setText(hour + ":" + min);
                        }
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, listener, hour, min, true);
                dialog.show();
                break;
            case R.id.localrelative:
                Intent intent=new Intent(view.getContext(), ChangeLocal.class);
                intent.putExtra("poi",localname.toString());
                intent.putExtra("resource",1);
                startActivity(intent);
                break;
            case R.id.buttonconf:
                    finish();
                break;
            case R.id.time_end:
                TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        if (hour < 10 && min < 10) {
                            view2.setText("0" + hour + ":" + "0" + min);
                        } else if (hour < 10) {
                            view2.setText("0" + hour + ":" + min);
                        } else if (min < 10) {
                            view2.setText(hour + ":" + "0" + min);
                        } else {
                            view2.setText(hour + ":" + min);
                        }
                    }
                };
                TimePickerDialog dialog1 = new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, listener1, hour, min, true);
                dialog1.show();
                break;
            default:
                break;
        }
    }

    private void getbeginDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }
    private void locationOption() {
        //1. 创建定位服务客户端类的对象
        locationClient =
                new LocationClient(getApplicationContext());
        //2. 创建定位客户端选项类的对象，并设置定位参数
        locationClientOption = new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll");
        //设置定位间隔时间
//        locationClientOption.setScanSpan(1000);
        //设置定位坐标系
//        SDKInitializer.setCoordType(CoordType.GCJ02);
        //设置定位模式:高精度模式
        locationClientOption.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy
        );

        //需要定位的地址数据
        locationClientOption.setIsNeedAddress(true);
        //需要地址描述
        locationClientOption.setIsNeedLocationDescribe(true);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(true);
        //3. 将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4. 开始定位
        locationClient.start();
        //5. 给定位客户端端类的对象注册定位监听器
        locationClient.registerLocationListener(
                new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        String addr = bdLocation.getAddrStr();
                        double lat = bdLocation.getLatitude();
                        double lng = bdLocation.getLongitude();
                        localname=new ArrayList<>();
                        List<Poi> pois = bdLocation.getPoiList();
                        for (Poi p:pois) {
                            String name = p.getName();
                            localname.add("name"+name);
                            String pAddr = p.getId();
                        }
                        localtv.setText(addr);
                        String l=getIntent().getStringExtra("local1");
                        if (l!=null){
                            localtv.setText(l);
                        }
                        showLocOnMap(lat, lng);
                    }
                }
        );
    }
    private void showLocOnMap(double lat, double lng) {
        //  获取定位图标setCoorType
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
                lat , lng );
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

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
    private void initializeMap() {
        baiduMap = mapView.getMap();
        uiSettings = baiduMap.getUiSettings();
        baiduMap.setTrafficEnabled(true);
    }
}
