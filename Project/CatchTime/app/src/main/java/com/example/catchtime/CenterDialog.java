package com.example.catchtime;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
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
import com.example.catchtime.activity.AddActivityDetial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    private LinearLayout btnl;
    private EditText act;
    private List<String> arrs;
    private String[] colors;
    private String color;
    private Handler handler;
    private  Button add_act;
    private TextView dialog_cancel;
    private UiSettings uiSettings;
    private TextView dialog_sure;
    private TextView newpalece_text_getname;
    //按钮类
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
        initializeMap();
        mapView.onResume();
        baiduMap = mapView.getMap();
        //设置图层定位
        baiduMap.setMyLocationEnabled(true);
        hideLogo();
        zoomLevelOp();
        locationOption();
        getData();//这个方法用来获取activity
        handler=new Handler() {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String info=(String)msg.obj;
                Log.e("info",info);
                arrs=new ArrayList<>();
                Type type=new TypeToken<List<com.example.catchtime.entity.Activity>>(){}.getType();
                Gson gson=new Gson();
                List<com.example.catchtime.entity.Activity> list = gson.fromJson(info.trim(),type);
                Log.e("error",list.toString());
                for(int i=0;i<list.size();i++){
                    String arr=new String();
                    arr=list.get(i).getActivity_name().toString();
                    Log.e("ii",arr);
                    arrs.add(arr);
                }
                Log.e("info00",arrs.toString());
            }
        };
    }

    private void initializeMap() {
            baiduMap = mapView.getMap();
            uiSettings = baiduMap.getUiSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newplacepopup);
        btnl=findViewById(R.id.btnl);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 6 / 7; // 设置dialog宽度为屏幕的4/5
        lp.height=display.getHeight() * 4 / 5;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        act=findViewById(R.id.act);
        newpalece_text_getname=findViewById(R.id.newpalece_text_getname);
        add_act();//自定义按钮实现跳转并添加activity
        dialog_cancel=findViewById(R.id.dialog_cancel);
        quxiao();
        dialog_sure=findViewById(R.id.dialog_sure);
        sure();
        Log.e("3","okkkkkk");
        colors=new String[]
                {"#CCFFFF","#FF6699","#CC6699","#CC33FF","#99CCFF","#993399","#33FFFF","#33CCCC","#990033","#CC6699","#CC3399","#CC00CC\t","#990066","#663399","#993300"
                        ,"#66CCCC","#999966","#339966","#CCFFFF","#99FF66","#CC66CC"};
        //当有数据的时候调用此方法；
        int d=arrs.size();
        if(d>0){
            initView();
        }
    }

    private void sure() {
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location_name=newpalece_text_getname.getText().toString().trim();
                String act_name=act.getText().toString().trim();
                Log.e("name",location_name);
                Log.e("name2",act_name);


            }
        });
    }

    private void quxiao() {
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//点击取消，弹窗关闭
            }
        });
    }

    private void add_act() {
        add_act=findViewById(R.id.add_act);
        add_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, AddActivityDetial.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int userid=1;
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivityController?userid="+userid);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("wer", "df" + info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void locationOption() {
        locationClient = new LocationClient(context);
        baiduMap.setMyLocationEnabled(true);
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll");
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
                        //将定位数据显示在地图上
                        showLocOnMap(lat, lng);
                        Log.e("xianshi",lat+lng+"");
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
                .latitude(lat)
                .longitude(lng)
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


    //实现动态创建button以及自动换行功能
    private void initView() {
        LinearLayout parentLL = findViewById(R.id.btnl);// contentView的布局
        int size = arrs.size(); // 添加Button的个数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 3, 10, 3);
        final ArrayList<Button> childBtns = new ArrayList<Button>();
        int totoalBtns = 0;
        for (int i = 0; i < size; i++) {
            String item = arrs.get(i).toString();
            color = colors[i];
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int length = item.length();
            if (length < 2) {// 根据字数来判断按钮的空间长度, 少于2个当一个按钮
                itemParams.weight = 1;
                totoalBtns++;
            } else if (length < 4) { // <4个两个按钮空间
                itemParams.weight = 2;
                totoalBtns += 2;
            } else {
                itemParams.weight = 3;
                totoalBtns += 3;
            }
            itemParams.width = 0;
            itemParams.setMargins(10, 2, 10, 2);
            final Button childBtn = (Button) LayoutInflater.from(context).inflate(R.layout.button_layout, null);
            childBtn.setText(item);
            childBtn.setTextColor(Color.parseColor(color));
            childBtn.setOnClickListener(new View.OnClickListener() {
                int n = 0;
                @Override
                public void onClick(View v) {
                    act.setText(childBtn.getText().toString());
                    Log.e("text",childBtn.getText().toString());
                }
            });
            childBtn.setTag(item);
            childBtn.setLayoutParams(itemParams);
            childBtns.add(childBtn);

            if(totoalBtns >= 5){
                LinearLayout horizLL = new LinearLayout(context);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(Button addBtn:childBtns){
                    horizLL.addView(addBtn);
                }
                parentLL.addView(horizLL);
                childBtns.clear();
                totoalBtns = 0;
            }
        }
        //最后一行添加一下
        if(!childBtns.isEmpty()){
            LinearLayout horizLL = new LinearLayout(context);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for(Button addBtn:childBtns){
                horizLL.addView(addBtn);
            }
            parentLL.addView(horizLL);
            childBtns.clear();
            totoalBtns = 0;
        }
    }
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}