package com.example.catchtime.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catchtime.Add_detailPage;
import com.example.catchtime.Login;
import com.example.catchtime.NewPalce;
import com.example.catchtime.R;
import com.example.catchtime.TimeAdapter;
import com.example.catchtime.TimeAdapterEnd;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.entity.Time;
import com.example.catchtime.entity.User;
import com.example.catchtime.setting.CircleImageView;
import com.example.catchtime.setting.UserInfor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private customListener listener;
    private ImageView unlogin;
    private View view;
    private SharedPreferences p;
    private ImageView add_activity;
    private ImageView help;
    private View inflate;
    private TextView camera;
    private int value;
    private TextView pic;
    private TextView cancel;
    private TextView usering_name;
    private TextView usering_moto;
    private CircleImageView circleImageView;
    private Dialog dialog;
    private Handler handler;
    private CircleImageView imageView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.settingfragment,null);
        p = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        if(getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        getViews();
        onLisener();
        sendMessage();
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Gson gson = new Gson();
                User user = new User();
                user = gson.fromJson(info,User.class);
                usering_name.setText(user.getUsername());
                usering_moto.setText(user.getMoto());
//                circleImageView.setImageResource(getDrawableID(user.getImage()));
            }
        };
        value=p.getInt("user_id",0);
        Log.e("value",value+"");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
            }
        });
        return view;
    }
    private void onLisener() {
        listener = new customListener();
        add_activity.setOnClickListener(listener);
        help.setOnClickListener(listener);
        unlogin.setOnClickListener(listener);
    }

    private void getViews() {
        add_activity = view.findViewById(R.id.add_activity);
        help = view.findViewById(R.id.help);
        unlogin = view.findViewById(R.id.btn_unlogin);
        imageView = view.findViewById(R.id.user_img);
        usering_name = view.findViewById(R.id.user_name);
        usering_moto = view.findViewById(R.id.user_infor);
        circleImageView = view.findViewById(R.id.user_img);
    }
    private void show(View view) {
        dialog = new Dialog(getContext(),R.style.DialogTheme);
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.change_photo, null);
        //初始化控件
        camera = (TextView) inflate.findViewById(R.id.camera);
        pic = (TextView) inflate.findViewById(R.id.pic);
        cancel = (TextView) inflate.findViewById(R.id.cancel);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    class customListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_activity:
                    Intent intent1=new Intent(view.getContext(), AddActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.help:
                    break;
                case R.id.btn_unlogin:
                    SharedPreferences preferences = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.clear();
                    preferences.edit().putBoolean("isFirstIn", true).commit();
                    editor.commit();
                    int a = p.getInt("user_id",0);
                    Log.e("zy",a+"");
                    Intent intent3 = new Intent(view.getContext(), Login.class);
                    startActivity(intent3);
                    break;
            }
        }
    }
    private void sendMessage() {
        Log.e("发数据啦","准备！");
        Log.e("id",p+"");
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/UserInfo?userId="+value);
                    Log.e("发送完数据啦","OK");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    if(null!=info) {
                        Log.e("ww", info);
                        wrapperMessage(info);
                    }
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
        handler.sendMessage(msg);
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
}
