package com.example.catchtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewPalce extends AppCompatActivity implements View.OnClickListener,CenterDialog.OnCenterItemClickListener {
    private Button button;
    private Button button1;
    private CenterDialog centerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceshi);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.ceshi);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ModifyPage.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(this);

        centerDialog = new CenterDialog(this,new int[]{R.id.dialog_cancel, R.id.dialog_sure});
        centerDialog.setOnCenterItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        centerDialog.show();
    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch(view.getId()){
            case R.id.dialog_sure:
                Toast.makeText(this,"确定按钮", Toast.LENGTH_SHORT).show();
                break;
             default:
                 break;
        }
    }
}
