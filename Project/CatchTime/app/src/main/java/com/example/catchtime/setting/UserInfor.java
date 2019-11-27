package com.example.catchtime.setting;

import androidx.appcompat.app.AppCompatActivity;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catchtime.R;

public class UserInfor extends SwipeBackActivity {
    private View inflate;
    private TextView camera;
    private TextView pic;
    private TextView cancel;
    private Dialog dialog;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getViews();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
            }
        });
    }

    private void show(View view) {
        dialog = new Dialog(this,R.style.DialogTheme);
        inflate = LayoutInflater.from(this).inflate(R.layout.change_photo, null);
        //初始化控件
        camera = (TextView) inflate.findViewById(R.id.camera);
        pic = (TextView) inflate.findViewById(R.id.pic);
        cancel = (TextView) inflate.findViewById(R.id.cancel);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    private void getViews() {
        imageView = (CircleImageView) findViewById(R.id.user_img);
    }
}