package com.example.catchtime.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.catchtime.R;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.catchtime.entity.Activity;
import com.example.catchtime.entity.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AddActivity extends SwipeBackActivity {
    private SwipeLayout swipeLayout;
    private List<Activity> lists;
    private ListView mListView;
    private TextView btnex;
    private TextView btnfin;
    private SharedPreferences p;
    private Handler handler;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        p = getSharedPreferences("user", MODE_PRIVATE);
        id = p.getInt("user_id", 0);
        lists = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.swipe_listview);


        btnex = (TextView) findViewById(R.id.btnex);
        btnfin = (TextView) findViewById(R.id.btnfin);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, AddActivityDetial.class);
                startActivity(intent);
            }
        });
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Log.e("info09", info);
                Type type = new TypeToken<List<Activity>>() {}.getType();
                Gson gson = new Gson();
                List<Activity> list = gson.fromJson(info.trim(), type);
                Log.e("erroror", list.toString());
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
                    Log.e("color", list.get(i).getIcon_color());
                    activity.setColor(color);
                    lists.add(activity);
                }
                Log.e("info", info);
                Log.e("lists",lists.toString());
                ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(), lists);
                mListView.setAdapter(listViewAdapter);
            }
        };
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
        int user_id = p.getInt("user_id", 0);
        User user = new User();
        user.setUser_id(id);
        Gson gson = new Gson();
        String userid = gson.toJson(user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://175.24.14.26:8080/Catchtime/ActivityController?userid=" + userid + "&&info=" + "findall");
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

    class ListViewAdapter extends BaseSwipeAdapter {

        private Context mContext;
        private List<Activity> lists;
        private int pos;

        public ListViewAdapter(Context context, List<Activity> lists) {
            this.mContext = context;
            this.lists = lists;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        @Override
        public View generateView(int position, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_swipelayout, null);
            pos = position;
            final SwipeLayout swipeLayout = view.findViewById(R.id.swipe);
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    super.onOpen(layout);
//                    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();

                }
            });
            return view;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
            ImageView imageView = convertView.findViewById(R.id.add_activity_imageview);
            ImageView tubiao = convertView.findViewById(R.id.add_activity_imageview);
            ImageView del = convertView.findViewById(R.id.delete);
            Log.e("tubiao", lists.get(position).getIcon_color() + "");
            Log.e("tubiao0", lists.get(position).getIcon_name() + "");
            Log.e("wenzi", lists.get(position).getActivity_name());
            tubiao.setBackgroundColor(convertView.getResources().getColor(lists.get(position).getColor()));
            tubiao.setImageResource(lists.get(position).getImage());
            TextView textView = convertView.findViewById(R.id.add_activity_text_view);
            textView.setText(lists.get(position).getActivity_name());
            del.setTag(position);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lists.remove(position);
                    notifyDataSetChanged();
                    sl.close();
                }
            });
//            swipeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(),"点击删除按钮",Toast.LENGTH_SHORT);
//                    list.remove(position);
//                    notifyDataSetChanged();
//                }
//            });
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private int getColorIDn(String string) {
            try {
                String name = string;
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

        private int getDrawableIDn(String str) {
            String color = str;
            try {
                Field field = R.color.class.getField(color);
                int ColorID = 0;
                ColorID = field.getInt(new R.color());
                return ColorID;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return 0;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}


