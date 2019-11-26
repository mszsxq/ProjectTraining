package com.example.catchtime.setting;

import androidx.appcompat.app.AppCompatActivity;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.os.Bundle;

import com.example.catchtime.R;

public class UserInfor extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}
