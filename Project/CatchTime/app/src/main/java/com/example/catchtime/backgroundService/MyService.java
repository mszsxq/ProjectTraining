package com.example.catchtime.backgroundService;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.PoiRegion;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.SpatialRelationUtil;

import com.example.catchtime.entity.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xdandroid.hellodaemon.AbsWorkService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;


public class MyService extends AbsWorkService {
    public static boolean sShouldStopService;
    private static boolean isrunning=false;
    private String userName;
    private String internetIp;//访问的ip
    private String LocationName;//位置的名称
    private BDLocation perbdlocation=new BDLocation();//表示前一次定位的地点
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private int stepingsec = 0;//运动的秒数
    private LatLng centerLatLng;//现在所在位置的中心点
    private List<Location> Locations;
    private boolean flagsteping = false;//代表是否在行走
    private int usingTime = 0;//表示当前活动花费的时间
    private String retrunType;
    private String activityName;
    private BDLocation activitybdlocation=null;//表示一个活动的地点
    private boolean flagagainsteping=false;//表示是否是刚刚停止行走  表示到达新地点之后 是否在4分钟之内继续行走 继续行走则4分钟属于行走  没继续行走判断是否为新地点
    //    是新地点则弹窗 不是新地点则保存当前活动名 地点
    private int againstepingsec=0;//表示刚刚停止步行之后 静止的秒数
    private boolean isagainsteping=false;//表示是否为继续行走
    private String screenFlag="ACTION_SCREEN_ON";//"ACTION_USER_PRESENT","ACTION_SCREEN_OFF";
    private String latestScreenOn="";//最近一次屏幕点亮的时间
    private String latestScreenOff="";//最近一次屏幕熄灭的时间
    private String latestScreenPresent;//最近一次解锁屏幕的时间
    private String lateststepstart;//最近一次行走的结束时间
    private String lateststepend;//最近一次行走的技术时间
    private SimpleDateFormat ft= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String latestActivityFinishTime;
    private double  distance=0;
    private String timeAll;//表示此次扫描开始的时间
    private static final String TAG = "LocationService";
    private final static String ACTION_START = "action_start";
    private boolean flagsleep=false;//表示今天是否睡觉睡觉的标志
//    private static boolean isrunning;
    /*根据用户的位置需要
     * 进行数据库改变的要求
     *   用户产生了长时间的行走（时间大于3分钟或者运动的距离大于300米）
     *   用户已经不在当前地点的范围之中
     *   用户进入陌生地点需要进行弹窗
     *   睡觉的判断
     *      在十点之后就没在打开手机 睡觉的起始时间为 最后一个点亮手机屏幕
     *   睡醒的判断
     *      通过语音唤醒手机
     *      在十分钟之内连续多次解锁手机
     *
     *   去除玩手机时间的影响
     *      保存打开手机的时间
     *      每次关闭手机 把手机解锁时间到锁屏时间传递给服务器端
     *      服务器端通过所给的手机时间对用户的活动时间进行修改
     * */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }
    public static void startService(Context context) {
    }

    @Override
    protected int onStart(Intent intent, int flags, int startId) {
        //加载一遍数据 location activity
        if (intent!=null){
            return super.onStart(intent, flags, startId);
        }
        return 0;
    }

    @Override
    public void startWork(Intent intent, final int flags, int startId) {
        Log.e("LocationService", "执行startwork 但是未调用方法");
        if (intent!=null){
            userName = intent.getStringExtra("username");
            Locations = (List<Location>) intent.getSerializableExtra("locations");
        }
        //获取常用地址 获取常用地址和活动之间的关系
        if (Locations == null) {
            Locations = new ArrayList<Location>();
            try {
                Locations=getLocations(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (latestActivityFinishTime == null) {
            latestActivityFinishTime = ft.format(new Date());
        }
        if (isrunning==false) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//        }

            Log.e("LocationService", "开始 执行startwork");

            locationClient = new LocationClient(getApplicationContext());
            locationClientOption = new LocationClientOption();
            locationClientOption.setOpenGps(true);
            locationClientOption.setScanSpan(10000);//每十秒进行一次扫描
            locationClientOption.setCoorType("bd09ll");
            locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            locationClientOption.setIsNeedAddress(true);
            locationClientOption.setIsNeedLocationDescribe(true);
            locationClientOption.setIsNeedLocationPoiList(true);
            locationClient.setLocOption(locationClientOption);
            locationClient.start();
//        Intent dialogIntent = new Intent(getBaseContext(), LocActivity.class);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getApplication().startActivity(dialogIntent);
//        Log.e("LocationService启动activity","");
            locationClient.registerLocationListener(new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    if (new Date().getHours()>22){

                    }
                    if (activitybdlocation == null) {
                        activitybdlocation = bdLocation;
                    }
                    //如果是第一次打开APP 进行初始化地址 名称
                    if (activityName==null){
                        boolean inlocation = false;
                        for (Location locationBean : Locations) {
                            Log.e("LocationService",locationBean.toString());
                            Log.e("LocationService",isRange(bdLocation.getLatitude(), bdLocation.getLongitude(), locationBean.getLocationLat(), locationBean.getLocationLng())+"");
                            Log.e("LocationService",DistanceUtil.getDistance(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())
                                ,new LatLng(locationBean.getLocationLat(), locationBean.getLocationLng()))+"");

                            if (isRange(bdLocation.getLatitude(), bdLocation.getLongitude(), locationBean.getLocationLat(), locationBean.getLocationLng())) {
                                inlocation = true;
                                LocationName = locationBean.getLocationName();
                                activityName = findActivitybylocation(LocationName);//name等于与此地点相关联的活动
                                activitybdlocation.setLatitude(locationBean.getLocationLat());
                                activitybdlocation.setLongitude(locationBean.getLocationLng());
                                centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                            }
                        }
                        // TODO: 2019/12/3 在这加上可识别地点的判断  方便用户进行使用
                        boolean inAbleLocation = false;
                        if (inlocation==false) {
                            retrunType = ableLocation(bdLocation.getPoiRegion());
                            if (retrunType == null) {
                                inAbleLocation = false;
                            } else {
                                inAbleLocation = true;
                                activityName = retrunType;
                                LocationName = activityName;
                                activitybdlocation.setLongitude(bdLocation.getLongitude());
                                activitybdlocation.setLatitude(bdLocation.getLatitude());
                                centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                            }
                        }
                        if (inlocation == false && inAbleLocation == false) {
                            Handler handlerThre = new Handler(Looper.getMainLooper());
                            handlerThre.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "此地点为新地点因此需要弹窗添加", Toast.LENGTH_LONG).show();
                                    try {
                                        writeExternal(getBaseContext(), "到达新的未知地点", null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            //此数据等于添加新地点之后的返回数据  进行判断是否设置了地点对应的活动
                            activityName = "";
                            LocationName = "";
                            activitybdlocation.setLongitude(bdLocation.getLongitude());
                            activitybdlocation.setLatitude(bdLocation.getLatitude());
                            centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                        }
                    }
                    //进行睡觉的判断
                    timeAll = ft.format(new Date());
                    usingTime += 10;
                    if (latestScreenPresent == null) {
                        latestScreenPresent = timeAll;
                    }
                    URL url;
                    final float speed = bdLocation.getSpeed();
                    List<Poi> pois = bdLocation.getPoiList();
//                PoiRegion poiRegion= bdLocation.getPoiRegion();
//                String poiDerectionDesc = poiRegion.getDerectionDesc();    //获取PoiRegion位置关系
//                String poiRegionName = poiRegion.getName();    //获取PoiRegion名称
//                String poiTags = poiRegion.getTags();    //获取PoiRegion类型
//                Log.e("poi"+poiDerectionDesc+"  ",poiRegionName+"   "+poiTags);
//                    for (Poi poi : pois) {
//                    Log.e("poi type:"+poi.getTags(),"poi name:"+poi.getName()+"  poi rank:"+poi.getRank()+
//                    poi.describeContents()+"   "+poi.getId()+" "+poi.getAddr());
                    try {
                        writeExternal(getBaseContext(),bdLocation.getPoiRegion().getTags()+ "   "+bdLocation.getPoiRegion().getName(),"pois.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    }
//                double distance = distance(bdLocation.getLatitude(),//纬度
//                        bdLocation.getLongitude(),
//                        perbdlocation.getLatitude(),
//                        perbdlocation.getLongitude());
//                distance=distance(114.52938,38.003656,114.529182,38.003588);
                    distance = DistanceUtil.getDistance(
                            new LatLng(bdLocation.getLongitude(),
                                    bdLocation.getLatitude()),
                            new LatLng(perbdlocation.getLongitude(),
                                    perbdlocation.getLatitude()));
                    if (distance > 0 || speed > 0) {//正常人的步行速度大概为1米每秒
                        stepingsec += 10;
                        if (flagsteping == false) {
                            lateststepstart = timeAll;//刚开始行走时 设置行走时间
                        }
                        flagsteping = true;//表示正在行走
                        againstepingsec = 0;//继续行走后静止的秒数

                        if (flagagainsteping == true) {//判断是否刚刚停止行走
                            isagainsteping = true;//设置为开始继续行走
                            flagagainsteping=false;
                        }
                    } else {//速度为零并且距离为零
                        flagsteping = false;//取消正在行走的标志
                        lateststepend = timeAll;//设置最近一次行走停止的时间

                        if (flagagainsteping == true) {//判断是否是刚刚停止行走
                            againstepingsec += 10;//重新行走的静止时间加10
                        }
                    }

                    if (againstepingsec > 60 && flagagainsteping == true) {//表示刚刚停止行走 但是在新地点的时间已经超过4分钟
                        againstepingsec = 0;//
                        flagagainsteping = false;//
                        //更改正在的活动和位置 对地点进行判断是否为新地点 新地点并且不能识别就进行弹窗 可以识别就默认 不是新地点就默认
                        //判断到达的地点是否为常用地点 是新地点弹窗

                        Handler handlerThree = new Handler(Looper.getMainLooper());
                        final double finalDistance = distance;
                        handlerThree.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "进行活动的变化", Toast.LENGTH_LONG).show();
                            }

                        });

                        boolean inlocation = false;
                        for (Location locationBean : Locations) {
                            if (isRange(bdLocation.getLatitude(), bdLocation.getLongitude(), locationBean.getLocationLat(), locationBean.getLocationLng())) {
                                inlocation = true;
                                LocationName = locationBean.getLocationName();
                                activityName = findActivitybylocation(LocationName);//name等于与此地点相关联的活动
                                activitybdlocation.setLatitude(locationBean.getLocationLat());
                                activitybdlocation.setLongitude(locationBean.getLocationLng());
                                centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                            }
                        }
                        // TODO: 2019/12/3 在这加上可识别地点的判断  方便用户进行使用
                        boolean inAbleLocation = false;
                        if (inlocation==false) {
                            retrunType = ableLocation(bdLocation.getPoiRegion());
                            if (retrunType == null) {
                                inAbleLocation = false;
                            } else {
                                inAbleLocation = true;
                                activityName = retrunType;
                                LocationName = activityName;
                                activitybdlocation.setLongitude(bdLocation.getLongitude());
                                activitybdlocation.setLatitude(bdLocation.getLatitude());
                                centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                            }
                        }
                        if (inlocation == false && inAbleLocation == false) {
                            Handler handlerThre = new Handler(Looper.getMainLooper());
                            handlerThre.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "此地点为新地点因此需要弹窗添加", Toast.LENGTH_LONG).show();
                                    try {
                                        writeExternal(getBaseContext(), "到达新的未知地点", null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            //此数据等于添加新地点之后的返回数据  进行判断是否设置了地点对应的活动
                            activityName = "";
                            LocationName = "";
                            activitybdlocation.setLongitude(bdLocation.getLongitude());
                            activitybdlocation.setLatitude(bdLocation.getLatitude());
                            centerLatLng = new LatLng(bdLocation.getLongitude(), bdLocation.getLatitude());
                        }

                    }
                    if (againstepingsec < 60 && isagainsteping == true) {//添加条件是用户进行了休息时间不到1分钟 并且继续行走
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //把两次行走之间的时间存入数据 算作行走; 时间为latestActivityFinishTime-当前时间
                                    URL url = new URL("https://cn.bing.com/");// TODO: 2019/12/2 添加完整的url地址
                                    url.openConnection();
                                    writeExternal(getBaseContext(), "起始时间:" + latestActivityFinishTime + "  结束时间:" + timeAll+"    继续行走", null);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        isagainsteping = false;
                        flagagainsteping = false;
                    }

                    if (flagsteping == false) {
                        //停止运动之后把行走时间存输入数据库  并且把行走的时间置零
                        if (stepingsec > 60) {//如果行走时间超过4分钟才存入到数据库
                            flagagainsteping = true;
                            //把行走的时间存入数据;  时间为lateststepend-lateststepstart
                            final String ss=latestActivityFinishTime;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        URL url = new URL("https://cn.bing.com/");// TODO: 2019/12/2 添加完整的url地址
                                        url.openConnection();
                                        // TODO: 2019/12/3 把前一个活动存入到数据库  起始时间为latestActivityFinishTime 结束时间为lateststepstart(当前-stepingsec) 活动名为activityname
                                        writeExternal(getBaseContext(), "起始时间:" + ss + "  结束时间:"+lateststepstart +"    "+activityName, null);
                                        // TODO: 2019/12/3 把行走存入到数据库  起始时间为lateststepstart 结束时间为lateststepend 活动名为行走
                                        writeExternal(getBaseContext(), "起始时间:" + lateststepstart + "  结束时间:"+lateststepend +"    行走", null);

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            usingTime = 0;//花费时间置零W
                            latestActivityFinishTime = timeAll;
                        }

                        stepingsec = 0;
                    } else if (flagsteping == true) {
                        //开始行走之间的活动也就停止 因此需要把之前的活动存入到数据库之中
//                    if (stepingsec > 240) {//如果行走的时间小于4分钟有可能是 （定位的准确性问题 或 短暂的移动） 这样情况下不会结束当前的活动
//                        try {
//                            if (stepingsec > 240) {//如果行走时间超过4分钟才存入到数据库
//                                usingTime = 0;
//                                url = new URL("https://cn.bing.com/");// TODO: 2019/12/2 添加完整的url地址
//                                url.openConnection();
//                                //判断到达的地点是否为常用地点 是新地点弹窗
//                                boolean inlocation=false;
//                                for (LocationBean locationBean:locationBeans){
//                                    if (isRange(bdLocation.getLatitude(),bdLocation.getLongitude(),locationBean.getLocationLat(),locationBean.getLocationLng())){
//                                        inlocation=true;
//                                    }
//                                }
//                                // TODO: 2019/12/3 在这加上可识别地点的判断  方便用户进行使用
//                                boolean inAbleLocation =false;
//                                retrunType=ableLocation(bdLocation.getPoiList());
//                                if (retrunType==null){inAbleLocation=false;}else{inAbleLocation=true;}
//                                if (inlocation==false&&inAbleLocation==false){
//                                if (inlocation==false&&inAbleLocation==false){
//                                    Handler handlerThree=new Handler(Looper.getMainLooper());
//                                    handlerThree.post(new Runnable(){
//                                        public void run(){
//                                            Toast.makeText(getApplicationContext() ,"此地点为新地点因此需要弹窗添加",Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                }
//                            }
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    }
                    Log.e("LocationService", "steping=" + stepingsec + ";usingtime=" + usingTime + "againstepsec" + againstepingsec + "   正在进行的活动"+activityName + timeAll);
                    Handler handlerThree = new Handler(Looper.getMainLooper());
                    final double finalDistance = distance;
                    handlerThree.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "steping=" + stepingsec + ";usingtime=" + usingTime + ";againstepsec=" + againstepingsec + "    " + speed + "    " + finalDistance + "   正在进行的活动"+activityName + timeAll, Toast.LENGTH_LONG).show();
                        }

                    });
                    perbdlocation = bdLocation;
                }
            });
        }
        isrunning=true;
    }

    private String findActivitybylocation(String locationName) {
        if (locationName.equals("食堂")){
            return "食堂";
        }
        if (locationName.equals("学院")){
            return "学院";
        }
        if (locationName.equals("宿舍")){
            return "宿舍";
        }
        return null;
    }

    //获取用户的常用地点列表
    private List<Location> getLocations(int userId) throws IOException {
        Log.e("LocationService","获取location列表");
//        final List<LocationBean> locationBeans = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
        //连接运行时恢复
//                try {
//                    URL url = null;// TODO: 2019/12/2 添加完整的url地址
//                    url = new URL("https://cn.bing.com/");
//                    URLConnection connection = url.openConnection();
//                    InputStream inputStream = connection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//                    String data = reader.readLine();
//                    Gson gson = new Gson();
//                    locationBeans = gson.fromJson(data, new TypeToken<ArrayList<String>>() {
//                    }.getType());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        }).start();

        Locations.add(new Location(1,"学院",38.003592,114.529362,0,""));
        Locations.add(new Location(2,"食堂",38.000681,114.527188,0,""));
        Locations.add(new Location(3,"宿舍",38.000127,114.524866,0,""));
        return Locations;
    }

    //计算两个经纬点之间的距离
    double distance(double lat1, double lon1, double lat2, double lon2) {
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
    static double deg2rad(double degree) {
        return degree / 180 * Math.PI;
    }

    //将弧度转换为角度
    static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

    //判断是否在指定的圆内
    private boolean isRange(double lat1, double lon1, double lat2, double lon2) {
//        return SpatialRelationUtil.isCircleContainsPoint(new LatLng(lat1, lon1), 100, new LatLng(lat2, lon2));
        return isCircleContainsPoint(new LatLng(lat1, lon1), 100, new LatLng(lat2, lon2));
    }

    //判断新地点能否识别
    private String ableLocation(PoiRegion poiRegion)  {
        try {
            writeExternal(getBaseContext(),poiRegion.getTags(),"pois.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (poiRegion!=null){
            if (poiRegion.getTags().contains("教育")){ return "教育"; }
            if (poiRegion.getTags().contains("餐饮")){ return "餐饮"; }
            if (poiRegion.getTags().contains("娱乐")){ return "娱乐"; }
            if (poiRegion.getTags().contains("休闲")){ return "娱乐"; }
        }
        return null;
    }

    //监听屏幕的亮灭以及系统的解锁以及语音唤醒
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void stringEvent(String s) throws ParseException {
        if (s.equals("ACTION_SCREEN_ON")){
            Log.e("LocationService","ACTION_SCREEN_ON");//屏幕点亮
            latestScreenOn =ft.format(new Date());
            screenFlag="ACTION_SCREEN_ON";
            Log.e("LocationService",latestScreenOn);
        }
        if (s.equals("ACTION_SCREEN_OFF")){
            Log.e("LocationService","ACTION_SCREEN_OFF");//屏幕熄灭
            latestScreenOff=ft.format(new Date());
            Log.e("LocationService",latestScreenOff);
            if((ft.parse(latestScreenOff).getTime() - ft.parse(latestScreenPresent).getTime()) > 60000) {
                //把活动的数据存储到 时间为latestActivityFinishTime到latestScreenPresent  活动名为activityname  地点为locationname  玩手机的时间超过一分钟进行存储
                final String ss=latestActivityFinishTime;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://cn.bing.com/");// TODO: 2019/12/2 添加完整的url地址
                            url.openConnection();
                            writeExternal(getBaseContext(),"起始时间:"+ss+"  结束时间:"+latestScreenPresent+"    "+activityName,null);
                            url = new URL("https://cn.bing.com/");// TODO: 2019/12/2 添加完整的url地址  把手机时间打包成json传达服务器端
                            url.openConnection();
                            writeExternal(getBaseContext(),"起始时间:"+latestScreenPresent+"  结束时间:"+latestScreenOff+"    手机娱乐",null);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                latestActivityFinishTime=latestScreenOff;
            }
            screenFlag="ACTION_SCREEN_OFF";
        }
        if (s.equals("ACTION_USER_PRESENT")){
            Log.e("LocationService","ACTION_USER_PRESENT");//系统解锁
            latestScreenPresent=ft.format(new Date());
            //如果五点之后解锁手机就认为起床
            if (ft.parse(latestScreenPresent).getHours()>=5&&ft.parse(latestScreenPresent).getHours()<=9&&flagsleep==false){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://cn.bing.com/");
                            url.openConnection();
                            writeExternal(getBaseContext(),"起始时间:"+latestScreenOff+"  结束时间:"+latestScreenPresent+"     睡觉",null);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            flagsleep=true;
//            latestActivityFinishTime=latestScreenPresent;
            Log.e("LocationService",latestScreenPresent);
            screenFlag="ACTION_USER_PRESENT";
        }
        if (s.equals("ACTION_CLOSE_SYSTEM_DIALOGS")){
            Log.e("LocationService","ACTION_CLOSE_SYSTEM_DIALOGS");//应用缩小 后台或窗口化
            screenFlag="ACTION_CLOSE_SYSTEM_DIALOGS";
        }
        if (s.equals("voice warm")&&flagsleep==false){
            Log.e("LocationService","voice warm");//应用缩小 后台或窗口化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("https://cn.bing.com/");
                        url.openConnection();
                        writeExternal(getBaseContext(),"起始时间:"+latestScreenOff+"  结束时间:"+latestScreenPresent+"      睡觉",null);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            flagsleep=true;
            latestActivityFinishTime=ft.format(new Date());
        }
    }


    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        Log.e("LocationService停止工作","");
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
        Log.e("LocationService被杀死","");
        isrunning=false;
//        Intent intent=new Intent(this,MyService.class);
//        startService(intent);
    }
    public static boolean isCircleContainsPoint(LatLng var0, int var1, LatLng var2) {
        if (var0 != null && var1 != 0 && var2 != null) {
            double var3 = DistanceUtil.getDistance(var0, var2);
            if (var3 > (double)var1) {
                return false;
            } else {
                return var3 == (double)var1 ? true : true;
            }
        } else {
            return false;
        }
    }
    public void writeExternal(Context context, String content,@Nullable String filename) throws IOException {
        if (filename==null){
            filename="catchtimetest.txt";
        }
        //获取外部存储卡的可用状态
        String storageState = Environment.getExternalStorageState();

        //判断是否存在可用的的SD Card
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            //路径： /data/data/com.example.cakeshop/files/catchtime.txt
            filename = getFilesDir().getAbsolutePath() +"/"+ filename;
//            FileOutputStream outputStream = new FileOutputStream(filename,true);
            OutputStreamWriter outputStream = new OutputStreamWriter(
                    new FileOutputStream(filename, true),//true代表是追加数据
                    "utf-8"
            );
            outputStream.write("\r\n");
            outputStream.write(content);
            outputStream.close();
        }
    }

}