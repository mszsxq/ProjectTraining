package com.example.catchtime.activity;

import android.app.Activity;
import android.os.Bundle;

import com.daimajia.swipe.SwipeLayout;
import com.example.catchtime.R;

import androidx.annotation.Nullable;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AddActivity extends SwipeBackActivity {
    private SwipeLayout swipeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        swipeLayout= (SwipeLayout) findViewById(R.id.swipelayout);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left,findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }



}
