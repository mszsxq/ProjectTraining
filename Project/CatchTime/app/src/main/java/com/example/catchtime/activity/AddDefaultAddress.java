package com.example.catchtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.catchtime.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddDefaultAddress extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);

        Intent intent = getIntent();
        String thing = intent.getStringExtra("name");
        TextView name = findViewById(R.id.aloc_ed_name);
        name.setText(thing);
        Button confirm = findViewById(R.id.aloc_tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
