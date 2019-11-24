package com.example.catchtime.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.catchtime.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class ActivitiesDetail extends SwipeBackActivity implements ObservableScrollViewCallbacks {

    private View mHeaderView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mBaseTranslationY;
    private ProgressBar progesss;
    private TextView min;
    private RelativeLayout view_dayoccupy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_detail);
        view_dayoccupy = (RelativeLayout) findViewById(R.id.view_dayoccupy1);
        progesss = (ProgressBar) findViewById(R.id.progesss1);
        min = (TextView) findViewById(R.id.min1);

        progesss.setProgress(66);
        min.setText(new StringBuffer().append(progesss.getProgress()).append("min"));
        setPosWay1();


    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
    //进度条
    private void setPosWay1() {
       min.post(new Runnable() {
            @Override
            public void run() {
                setPos();
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPos();
        }
    }
    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int w = getWindowManager().getDefaultDisplay().getWidth();
        Log.e("w=====", "" + w);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) min.getLayoutParams();
        int pro = progesss.getProgress();
        int tW = min.getWidth();
        if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = (int) (w - tW * 1.1);
        } else if (w * pro / 100 < tW * 0.7) {
            params.leftMargin = 0;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
        }
        min.setLayoutParams(params);

    }

}
