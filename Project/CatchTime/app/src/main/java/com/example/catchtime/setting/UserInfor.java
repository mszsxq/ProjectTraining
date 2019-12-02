package com.example.catchtime.setting;

import androidx.appcompat.app.AppCompatActivity;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catchtime.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserInfor extends SwipeBackActivity {
    private View inflate;
    private TextView camera;
    private TextView pic;
    private TextView cancel;
    private TextView usering_name;
    private TextView usering_phone;
    private TextView usering_moto;
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
        Intent intent = getIntent();
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒 E");
//        String s2 = dateFormat.format(date);
//        String s1= intent.getStringExtra("time");
//        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar=new GregorianCalendar();
//        Date d1= null;
//        try {
//            d1 = df.parse(s1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Date d2= null;
//        try {
//            d2 = df.parse(s2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long min = (d1.getTime()-d2.getTime())/(60*60*1000*24);
        usering_name.setText(intent.getStringExtra("name"));
        usering_phone.setText(intent.getStringExtra("phone"));
        usering_moto.setText(intent.getStringExtra("moto"));
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
        usering_name = (TextView) findViewById(R.id.user_name);
        usering_moto = (TextView) findViewById(R.id.user_infor);
        usering_phone = (TextView) findViewById(R.id.user_phone);
    }
}
