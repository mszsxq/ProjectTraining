package com.example.catchtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JumpTo extends AppCompatActivity {
    private boolean isFirstIn=true;//判断是否是第一次进入APP
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startActivity(new Intent(JumpTo.this, MainActivity.class));
                    finish();
                    break;
                case 1://第一次进入app
                    startActivity(new Intent(JumpTo.this, Login.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_pagelayout);
        init();
    }
    private void init() {
        //判断是否是第一次进入app
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        if (isFirstIn){
            handler.sendEmptyMessageDelayed(1, 1000);
            preferences.edit().putBoolean("isFirstIn", false).commit();
        } else {
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }
}
