package com.example.catchtime.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;

import com.example.catchtime.R;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitiesDetail extends AppCompatActivity implements ObservableScrollViewCallbacks {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_detail);
//        ObservableListView listView = (ObservableListView) findViewById(R.id.list);
//        listView.setScrollViewCallbacks(this);
//        ArrayList<String> items = new ArrayList<String>();
//        for (int i = 1; i <= 100; i++) {
//            items.add("Item " + i);
//        }
//        listView.setAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
}
