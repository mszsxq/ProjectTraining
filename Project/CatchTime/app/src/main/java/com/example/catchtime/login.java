package com.example.catchtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private TextView btn_fpwd;
    private CustomOnclickListener listener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getViews();
        registers();
    }

    private void registers() {
        listener=new CustomOnclickListener();
        btn_login.setOnClickListener(listener);
        btn_register.setOnClickListener(listener);
        btn_fpwd.setOnClickListener(listener);

    }

    private void getViews() {
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        btn_fpwd=findViewById(R.id.btn_fpwd);
    }
   class CustomOnclickListener implements View.OnClickListener{

       @Override
       public void onClick(View v) {
           switch (v.getId()){
               case R.id.btn_login:
                   break;
               case R.id.btn_register:
                   Intent intent=new Intent();
                   intent.setClass(login.this,register.class);
                   startActivity(intent);
                   overridePendingTransition(
                           R.anim.in,//进入动画
                           R.anim.out//出去动画
                   );
                   break;
               case R.id.btn_fpwd:
                   Intent intent1=new Intent();
                   intent1.setClass(login.this,forgetpwd.class);
                   startActivity(intent1);
                   break;

           }

       }
   }

}
