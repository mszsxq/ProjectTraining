package com.example.catchtime;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private EditText full_re;
    private CustomOnclickListner listner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        full_re=findViewById(R.id.full_re);
        registers();
        full_re.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>11){
                    full_re.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }else{
                    full_re.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void registers() {
        listner=new CustomOnclickListner();
        btn_login.setOnClickListener(listner);
    }

    class CustomOnclickListner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    Intent intent=new Intent();
                    intent.setClass(Register.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in,R.anim.out);
                    break;
            }

            }
        }
}
