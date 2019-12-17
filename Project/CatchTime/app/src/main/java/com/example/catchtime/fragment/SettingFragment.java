package com.example.catchtime.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.catchtime.Add_detailPage;
import com.example.catchtime.Login;
import com.example.catchtime.NewPalce;
import com.example.catchtime.R;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.setting.UserInfor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private customListener listener;
    private Button unlogin;
    private View view;
    private SharedPreferences p;
    private Button tui;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.settingfragment,null);
        p = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        getViews();
        onLisener();
        return view;
    }
    private void onLisener() {
        listener = new customListener();
        relativeLayout1.setOnClickListener(listener);
        relativeLayout2.setOnClickListener(listener);
        relativeLayout3.setOnClickListener(listener);
        relativeLayout4.setOnClickListener(listener);
        unlogin.setOnClickListener(listener);
        tui.setOnClickListener(listener);
    }

    private void getViews() {
        relativeLayout1 = (RelativeLayout) view.findViewById(R.id.person);
        relativeLayout2 =(RelativeLayout) view.findViewById(R.id.addactivity);
        relativeLayout3 =(RelativeLayout) view.findViewById(R.id.settings);
        relativeLayout4 =(RelativeLayout) view.findViewById(R.id.helping);
        unlogin = view.findViewById(R.id.btn_unlogin);
        tui=view.findViewById(R.id.tui);
    }

    class customListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.person:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserInfor.class);
                    startActivity(intent);
                    break;
                case R.id.addactivity:
                    Intent intent1=new Intent(view.getContext(), AddActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.settings:
                    Intent intent2=new Intent(view.getContext(), Add_detailPage.class);
                    startActivity(intent2);
                    break;
                case R.id.helping:
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
                case R.id.tui:
                    Intent intent9=new Intent();
                    intent9.setClass(view.getContext(), NewPalce.class);
                    startActivity(intent9);
            }
        }
    }
    private void toServer(int user_id) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.217.1:8080/Catchtime/UserInfo?user_id="+user_id+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
                    //wrapperMessage(info);
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
}
