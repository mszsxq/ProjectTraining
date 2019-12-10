package com.example.catchtime.backgroundService;

import android.os.Bundle;

import com.example.catchtime.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimedPopup extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timedpopup);
    }
}
