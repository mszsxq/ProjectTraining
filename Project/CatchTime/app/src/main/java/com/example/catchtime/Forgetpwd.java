package com.example.catchtime;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Forgetpwd extends AppCompatActivity {
    private TextView full;
    private EditText user_phone;
    private ImageView eyes2;
    private ImageView eyes3;
    private EditText user_pwd2;//输入密码
    private EditText user_pwd3;//再次输入密码
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;
    private CustomOnclickListner listner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        getviews();
        registers();
        //监听输入框的字数
        user_phone.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                full.setText(String.format("%d/11",input.length()));
                if(input.length()>11){
                    user_phone.setTextColor(getResources().getColor(R.color.red));
                    full.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else{
                    user_phone.setTextColor(getResources().getColor(R.color.black));
                    full.setTextColor(getResources().getColor(R.color.black));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void registers() {
        listner=new CustomOnclickListner();
        eyes2.setOnClickListener(listner);
        eyes3.setOnClickListener(listner);
    }

    private void getviews() {
        full=findViewById(R.id.full);
        user_phone=findViewById(R.id.user_phone);
        eyes2=findViewById(R.id.eyes2);
        eyes3=findViewById(R.id.eyes3);
        user_pwd2=findViewById(R.id.user_pwd2);
        user_pwd3=findViewById(R.id.user_pwd3);
    }
    class CustomOnclickListner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.eyes2:
                    if(isHideFirst==true){
                        eyes2.setImageResource(R.drawable.openeye);
                        HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();
                        user_pwd2.setTransformationMethod(method);
                        isHideFirst=false;
                    }else{
                        eyes2.setImageResource(R.drawable.closeeye);
                        TransformationMethod method1= PasswordTransformationMethod.getInstance();
                        user_pwd2.setTransformationMethod(method1);
                        isHideFirst=true;
                    }
                    //光标的位置
                    int index=user_pwd2.getText().toString().length();
                    user_pwd2.setSelection(index);
                    break;
                case R.id.eyes3:
                    if(isHideFirst==true){
                        eyes3.setImageResource(R.drawable.openeye);
                        HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();
                        user_pwd3.setTransformationMethod(method);
                        isHideFirst=false;
                    }else{
                        eyes3.setImageResource(R.drawable.closeeye);
                        TransformationMethod method1= PasswordTransformationMethod.getInstance();
                        user_pwd3.setTransformationMethod(method1);
                        isHideFirst=true;
                    }
                    //光标的位置
                    int index3=user_pwd3.getText().toString().length();
                    user_pwd3.setSelection(index3);

                    break;
            }
        }
    }
}