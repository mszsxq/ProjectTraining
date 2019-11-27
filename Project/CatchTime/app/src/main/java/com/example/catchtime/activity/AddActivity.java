package com.example.catchtime.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.catchtime.R;

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
    private List<String> list;
    private List<String> listName;
    private List<String> listNColor;
    private ListView mListView;
    private TextView btnex;
    private TextView btnfin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        list=new ArrayList<String>();
        list.add("onfeet");
        list.add("onfeet");
        list.add("onfeet");
        list.add("onfeet");
        list.add("onfeet");
        list.add("onfeet");
        listName = new ArrayList<String>();
        listName.add("学习");
        listName.add("走路");
        listName.add("读书");
        listName.add("睡觉");
        listName.add("吃饭");
        listName.add("娱乐");
        listNColor = new ArrayList<String>();
        listNColor.add("#FF906E");
        listNColor.add("#E17E1A");
        listNColor.add("#71B100");
        listNColor.add("#FFABAB");
        listNColor.add("#678BFD");
        listNColor.add("#E17E1A");

        mListView= (ListView) findViewById(R.id.swipe_listview);
        ListViewAdapter listViewAdapter=new ListViewAdapter(this,list,listName,listNColor);
        mListView.setAdapter(listViewAdapter);
        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setClass(AddActivity.this,AddActivityDetial.class);
                startActivity(intent);
            }
        });
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class ListViewAdapter extends BaseSwipeAdapter{

        private Context mContext;
        private List<String> list;
        private List<String> listName;
        private List<String> listColor;
        private int pos;
        public  ListViewAdapter(Context context,List<String> list,List<String> listName,List<String> listColor){
            this.mContext=context;
            this.list=list;
            this.listName=listName;
            this.listColor=listColor;
        }
        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        @Override
        public View generateView(int position, ViewGroup parent) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.activity_swipelayout,null);
            pos=position;
            final SwipeLayout swipeLayout=view.findViewById(R.id.swipe);
            swipeLayout.addSwipeListener(new SimpleSwipeListener(){
                @Override
                public void onOpen(SwipeLayout layout) {
                    super.onOpen(layout);
//                    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));

                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick",Toast.LENGTH_SHORT).show();

                }
            });
            return view;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            final SwipeLayout sl = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
            ImageView imageView=convertView.findViewById(R.id.add_activity_imageview);
            ImageView tubiao=convertView.findViewById(R.id.add_activity_imageview);
            ImageView del=convertView.findViewById(R.id.delete);
            tubiao.setBackgroundColor(Color.parseColor(listColor.get(position)));
            int imid = getResources().getIdentifier(list.get(position), "drawable", getPackageName());
            tubiao.setImageResource(imid);
            TextView textView=convertView.findViewById(R.id.add_activity_text_view);
            textView.setText(listName.get(position).toString());
            del.setTag(position);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    listName.remove(position);
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
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }





}
