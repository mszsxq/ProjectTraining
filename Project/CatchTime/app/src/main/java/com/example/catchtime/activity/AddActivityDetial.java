package com.example.catchtime.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

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
        mGridView= (GridView) findViewById(R.id.gridlist);
        mMyadapter=new MyAdapter(names,this);
        mGridView.setAdapter(mMyadapter);
    }
}
class MyAdapter extends BaseAdapter {
    private List<String> list;  //表示图片的名称  从而通过名称获得资源id
    private Context context;
    private static int getViewTimes = 0;
    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;

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
        return imageView;
    }
    class ViewHolder {
        ImageView img;
    }
}