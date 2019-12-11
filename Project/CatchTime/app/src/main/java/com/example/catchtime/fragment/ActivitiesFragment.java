package com.example.catchtime.fragment;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.activity.AddActivityDetial;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ActivitiesFragment extends Fragment {
    View view;
    private ListView listView;
    private MyAdapterActivities myAdapterActivities;
    private Handler handler;
    private SharedPreferences p;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test", "初始化第1个页面");
        view = inflater.inflate(R.layout.activitiesfragment, container, false);
        //存放数据的list；
        final List<Activity> list = new ArrayList<Activity>();
        listView = (ListView) view.findViewById(R.id.listview);
        final List<Activity> lists = new ArrayList<>();
        p=getContext().getSharedPreferences("user",MODE_PRIVATE);
        //--------------------------------------------------------------------------------------------------
        getData();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Log.e("info", info);
                Type type = new TypeToken<List<Activity>>() {
                }.getType();
                Gson gson = new Gson();

                List<Activity> list = gson.fromJson(info.trim(), type);
                Log.e("error", list.toString());

                for (int i = 0; i < list.size(); i++) {
                    Activity activity = new Activity();
                    activity.setIcon_name(list.get(i).getIcon_name());
                    String str = new String();
                    str = list.get(i).getIcon_name();
                    int img = getDrawableID(str);
                    activity.setImage(img);
                    activity.setActivity_name(list.get(i).getActivity_name());
                    String string = new String();
                    string = list.get(i).getIcon_color();
                    int color = getColorID(string);
                    activity.setIcon_color(list.get(i).getIcon_color());
                    activity.setColor(color);
                    lists.add(activity);
                }
                Log.e("info", info);
                listView = (ListView) view.findViewById(R.id.listview);
                listView.setAdapter(new MyAdapterActivities(getActivity(), lists, R.layout.activitiesfragment_litem));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), ActivitiesDetail.class);
                        intent.putExtra("color", lists.get(position).getColor());
                        intent.putExtra("activity_name",lists.get(position).getActivity_name());
                        startActivity(intent);
                    }
                });

            }
        };

        //----------------------------------------------------------------------------------------------------
        ImageView imageView = view.findViewById(R.id.addactivity);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddActivityDetial.class);
                startActivity(intent);
            }
        });
//        Button button2 = view.findViewById(R.id.detail);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), ActivitiesDetail.class);
//                startActivity(intent);
//            }
//        });
//        Button button1 = view.findViewById(R.id.jump_login);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(view.getContext(), Login.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    //由图片名称转换为资源文件
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //由颜色名称转换为资源文件
    private int getColorID(String str) {
        String color = str;
        try {
            Field field = R.color.class.getField(color);
            int ColorID = 0;
            ColorID = field.getInt(new R.color());
            return ColorID;//colorID就是R.color.name
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void getData() {
        int user_id=p.getInt("user_id",0);
        User user=new User();
        user.setUser_id(user_id);
        Gson gson = new Gson();
        String userid = gson.toJson(user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivityController?userid=" + userid+"&&info="+"findall");
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

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}