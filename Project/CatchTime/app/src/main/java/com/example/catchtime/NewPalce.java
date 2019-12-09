package com.example.catchtime;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewPalce extends AppCompatActivity implements View.OnClickListener,CenterDialog.OnCenterItemClickListener {
    private CenterDialog centerDialog;
    private Button button;
    private Button button1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceshi);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.ceshi);

        centerDialog = new CenterDialog(this,new int[]{R.id.button});
        centerDialog.setOnCenterItemClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        centerDialog.show();
    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
    }
}
