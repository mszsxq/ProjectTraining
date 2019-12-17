package com.example.catchtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.location.ChangeLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddLocation extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private UiSettings uiSettings;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private TextView local;
    private EditText editText;
    private double lat;
    private double lng;
    private ImageView thing;
    private ImageButton changeLocal;
    private List<String> poiss;
    private Button confirm;
    private ImageView back;
    private CustomListener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.add_location);
        mapView = findViewById(R.id.bmapView);
        Log.i("sss","11111111");Toast.makeText(this,"ssss",Toast.LENGTH_SHORT);
        initializeMap();
        hideLogo();
        zoomLevelOp();
        LocationOption();
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        init();
        initListener();
    }

    private void initListener() {
        listener = new CustomListener();
        confirm.setOnClickListener(listener);
        back.setOnClickListener(listener);
        thing.setOnClickListener(listener);
        changeLocal.setOnClickListener(listener);
    }

    public void init(){
        local = findViewById(R.id.aloc_ed_local);
        editText = findViewById(R.id.aloc_ed_name);
        changeLocal=findViewById(R.id.changelocal);
        confirm=findViewById(R.id.aloc_tv_confirm);
        back = findViewById(R.id.aloc_iv_back);
        thing = findViewById(R.id.aloc_iv_thing);
    }
    private void initializeMap() {
        baiduMap = mapView.getMap();
        uiSettings = baiduMap.getUiSettings();
    }

    private void LocationOption() {
        locationClient = new LocationClient(getApplicationContext());
        baiduMap.setMyLocationEnabled(true);
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
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
                local.setText(addr);
                String locastring=getIntent().getStringExtra("local1");
                if (locastring!=null){
                    local.setText(locastring);
                }
                lat = bdLocation.getLatitude();
                lng = bdLocation.getLongitude();
                List<Poi> pois = bdLocation.getPoiList();
                poiss=new ArrayList<>();
                for (Poi p:pois){
                    String name = p.getName();
                    String pAddr = p.getAddr();
                    Log.i("mmy"+poiss.size(),"POI:"+name+":"+pAddr);
                    poiss.add("name"+name);
                }
                String time = bdLocation.getTime();
                showLocOnMap(lat,lng);
            }
        });
    }

    private void showLocOnMap(double lat, double lng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.waters);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS,
                false,
                icon);
        baiduMap.setMyLocationConfiguration(config);
        MyLocationData locData = new MyLocationData
                .Builder()
                .latitude(lat)
                .longitude(lng)
                .build();
        baiduMap.setMyLocationData(locData);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));
        baiduMap.animateMapStatus(msu);
    }

    private void hideLogo(){
        mapView.removeViewAt(1);
    }
    private void zoomLevelOp() {
        baiduMap.setMaxAndMinZoomLevel(21, 13);
        MapStatusUpdate msu = MapStatusUpdateFactory
                .zoomTo(19);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    class CustomListener implements  View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.aloc_tv_confirm:
                    if(editText.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(),"请输入地点名称",Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent=new Intent(view.getContext(), AddActivityDetial.class);
                        intent.putExtra("locationName",editText.getText().toString().trim());
                        intent.putExtra("detailName",local.getText().toString().trim());
                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);
                        Log.e("ss",editText.getText().toString().trim()+local.getText().toString().trim()+"100"+lng);
                        startActivity(intent);
                        finish();

                    }
                    break;
                    case R.id.aloc_iv_back:
                        finish();
                        break;
                case R.id.aloc_iv_thing:
                    Intent intent2 = new Intent();
                    intent2.setClass(getApplicationContext(), AddActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.changelocal:
                    Intent intent1=new Intent(view.getContext(),ChangeLocal.class);
                    intent1.putExtra("poi",poiss.toString());
                    intent1.putExtra("resource",0);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    }
}
