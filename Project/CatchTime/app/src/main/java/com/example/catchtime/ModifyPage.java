package com.example.catchtime;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.daimajia.swipe.SwipeLayout;
import com.example.catchtime.chart.InitPieChart;
import com.example.catchtime.entity.activity_location;
import com.example.catchtime.entity.chartData;
import com.example.catchtime.fragments.ClickActivityFragment;
import com.example.catchtime.fragments.ClickLocationFragment;
import com.example.catchtime.location.ChangeLocal;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ModifyPage extends SwipeBackActivity implements View.OnClickListener {
    private activity_location al;
    private String activityName;
    private String date;
    private MapView mapView;
    private TextView view1;
    private TextView view2;
    private TextView location_name;
    private TextView activity_name;
    private ImageView icon;
    private int hour, min;
    private Calendar cal;
    private UiSettings uiSettings;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private BaiduMap baiduMap;
    private RelativeLayout localrelative;
    private RelativeLayout clickActivity;
    private RelativeLayout location;
    private TextView localtv;
    private List<String> localname;
    private Button buttonconf;
    private int updataActivity;
    private String updateLocation;
    private int updataIcon;
    private String  oldName;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    String info = (String) msg.obj;
                    Gson gson = new Gson();
                    al = gson.fromJson(info, activity_location.class);
                    showLocOnMap(al.getLat(),al.getLng());
                    localtv.setText(al.getDetailAdd());
                    location_name.setText(al.getLocation_name());
                    activity_name.setText(al.getActivity_name());
                    view1.setText(al.getStartTime());
                    view2.setText(al.getEndTime());
                    icon.setImageResource(getDrawableID(al.getIcon()));
                    icon.setBackgroundColor(getColorID(al.getColor()));
                    updateLocation=al.getLocation_name();
                    Log.i("检测","al"+al.toString());
                    break;
                case 1:
                    Bundle bundle = (Bundle) msg.obj;
                    updataActivity = bundle.getInt("activityId");
                    icon.setImageResource(getDrawableID(bundle.getString("iconName")));
                    icon.setBackgroundColor(getColorID(bundle.getString("color")));
                    activity_name.setText(bundle.getString("activityName"));
                    updataIcon = bundle.getInt("iconId");
                    break;
                case 2:
                    Bundle bundle1 = (Bundle) msg.obj;
                    updateLocation = bundle1.getString("locationName");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.modifypage);
        Intent intent = getIntent();
        activityName=intent.getStringExtra("activity_name");
        oldName=activityName;
        date=intent.getStringExtra("date");
        getViews();
        toServer();
        initializeMap();
        hideLogo();
        zoomLevelOp();
        getbeginDate();
        getoverDate();
    }

    private void showLocOnMap(double lat, double lng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.locstar);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("iconName",data.getStringExtra("iconName"));
            bundle.putInt("activityId",data.getIntExtra("activity_id",0));
            bundle.putString("color",data.getStringExtra("color"));
            bundle.putString("activityName",data.getStringExtra("activity_name"));
            bundle.putInt("iconId",data.getIntExtra("iconId",0));
            Log.i("检测","data"+bundle.getInt("activityId")+"b"+data.getIntExtra("activity_id",0));
            msg.obj=bundle;
            msg.what=1;
            handler.sendMessage(msg);
        }else if(resultCode ==300){
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("locationName",data.getStringExtra("locationName"));
            msg.obj=bundle;
            msg.what=2;
            handler.sendMessage(msg);
        }


    }
    private int getDrawableID(String str) {
        try {
            String name = str;
            Field field = R.drawable.class.getField(name);
            int DrawableID = 0;
            DrawableID = field.getInt(new R.drawable());
            return DrawableID;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private int getColorID(String str) {
        try {
            String name = str;
            Field field = R.color.class.getField(name);
            int ColorID = 0;
            ColorID = field.getInt(new R.drawable());
            return ColorID;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private void getoverDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }

    private void getViews(){
        mapView = (MapView) findViewById(R.id.bbmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        localrelative= (RelativeLayout) findViewById(R.id.localrelative);
        localtv= (TextView) findViewById(R.id.tvcontent);
        view1 = (TextView) findViewById(R.id.time_start);
        view2 = (TextView) findViewById(R.id.time_end);
        icon = (ImageView) findViewById(R.id.icon);
        view1.setOnClickListener((View.OnClickListener) this);
        view2.setOnClickListener((View.OnClickListener) this);
        localrelative.setOnClickListener(this);
        buttonconf= (Button) findViewById(R.id.buttonconf);
        buttonconf.setOnClickListener(this);
        location_name= (TextView) findViewById(R.id.location_name);
        activity_name= (TextView) findViewById(R.id.activity_name);
        clickActivity= (RelativeLayout) findViewById(R.id.clickActivity);
        clickActivity.setOnClickListener(this);
        location = (RelativeLayout) findViewById(R.id.location_layout);
        location.setOnClickListener(this);
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
                updateLocation=location_name.getText().toString().trim();
                changeToServer(updataIcon,updataActivity,updateLocation,oldName);
                Intent intent1 = new Intent();
                intent1.setClass(this, MainActivity.class);
                startActivity(intent1);
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
            case R.id.clickActivity:
                Intent intentFra = new Intent();
                intentFra.setClass(this, ClickActivityFragment.class);
                startActivityForResult(intentFra,100);
                break;
            case R.id.location:
                Intent intentLoc = new Intent();
                intentLoc.setClass(this, ClickLocationFragment.class);
                startActivityForResult(intentLoc,200);
                break;
            default:
                break;
        }
    }

    private void changeToServer(int iconId,int activityId,String locationId,String oldName) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.42.184:8080/Catchtime/changeActivityAfter?activityId="+activityId+"&locationName="+locationId+"&iconId="+iconId+"&oldName="+oldName+"&userId="+1+"&date="+date+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void getbeginDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();

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
                .zoomTo(18);
        baiduMap.setMapStatus(msu);
    }
    private void initializeMap() {
        baiduMap = mapView.getMap();
        uiSettings = baiduMap.getUiSettings();
        baiduMap.setTrafficEnabled(true);
    }

//    private void showMarkerOption() {
//        //1. 创建标注覆盖物显示位置的LatLng对象
//        final LatLng latLng = new LatLng(al.getLat(), al.getLng());
//        //2. 创建标注覆盖物对象
//        BitmapDescriptor icon =
//                BitmapDescriptorFactory.fromResource(
//                        R.mipmap.locstar);
//        OverlayOptions markerOption =
//                new MarkerOptions()
//                        .draggable(true)
//                        .alpha(0.8f)//透明度
//                        .icon(icon)//覆盖物图标
//                        .position(latLng)//覆盖物位置
//                        .rotate(45)//旋转角度，逆时针为正
//                        .perspective(true);//是否支持近大远小效果
//        //3. 把标注覆盖物添加到地图上
//        Overlay myMarker = baiduMap.addOverlay(markerOption);
//
//        //4. 给覆盖物添加监听器
//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                //显示经纬度坐标
//                double lat = latLng.latitude;
//                double lng = latLng.longitude;
//                Toast.makeText(
//                        getApplicationContext(),
//                        latLng.toString(),
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        showLocOnMap(al.getLat(),al.getLng());
//    }
    private void toServer(){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.42.184:8080/Catchtime/changeActivity?activityName="+activityName+"&date="+date+"&user_id="+1+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what=0;
        handler.sendMessage(msg);
    }
}
