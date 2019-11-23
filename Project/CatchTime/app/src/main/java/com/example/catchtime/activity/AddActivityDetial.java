package com.example.catchtime.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.baidu.mapapi.map.Marker;
import com.example.catchtime.R;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AddActivityDetial extends SwipeBackActivity {
    private GridView mGridView;
    public MyAdapter mMyadapter;
    private List<String> names;
    private TextView btnfin;
    private TextView btnex;
    private int pos=0;//返回最终选择了哪个图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivitydetial);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        names=new ArrayList<>();
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");
        names.add("onfeet");

        btnex= (TextView) findViewById(R.id.btnex);
        btnfin= (TextView) findViewById(R.id.btnfin);
        mGridView= (GridView) findViewById(R.id.gridlist);
        mMyadapter=new MyAdapter(names,this);
        mGridView.setAdapter(mMyadapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyadapter=new MyAdapter(names,view.getContext(),position);
                mGridView.setAdapter(mMyadapter);
                pos=position;
            }
        });

        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
class MyAdapter extends BaseAdapter {
    private List<String> list;  //表示图片的名称  从而通过名称获得资源id
    private Context context;
    private int i=-1;
    private static int getViewTimes = 0;
    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public MyAdapter(List<String> list, Context context,int i) {
        this.list = list;
        this.context = context;
        this.i=i;
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        Drawable cachedImage = null;
        if (null == convertView) {
            imageView = new ImageView(context);

            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Resources res=context.getResources();
        int picid = res.getIdentifier(list.get(position),"drawable",context.getPackageName());
        imageView.setImageResource(picid);
        if (i==position){
            imageView.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }
//        imageView.setOnClickListener(new View.OnClickListener() {
//            //设置点击按钮
//            @Override
//            public void onClick(View v) {
//                imageView.setBackgroundColor(context.getResources().getColor(R.color.gray));
//                Log.e("sssssss","aaaaaaaaaaaa");
////                imageView.setBackground();
//            }
//        });
        return imageView;
    }
    class ViewHolder {
        ImageView img;
    }
}