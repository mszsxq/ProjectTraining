package com.example.catchtime;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class Register extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private EditText full_re;
    private TextView full;
    private ImageView eyes1;
    private EditText user_pwd1;
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;
    private CustomOnclickListner listner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getviews();
        registers();
        // 监听号码输入框的字数
        full_re.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                full.setText(String.format("%d/11",input.length()));
                if(s.toString().trim().length()>11){
                    full_re.setTextColor(getResources().getColor(R.color.red));
                    full.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else{
                    full_re.setTextColor(getResources().getColor(R.color.black));
                    full.setTextColor(getResources().getColor(R.color.black));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void getviews() {
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        full_re=findViewById(R.id.full_re);
        full=findViewById(R.id.full);
        user_pwd1=findViewById(R.id.user_pwd1);
        eyes1=findViewById(R.id.eyes1);
    }
    private void registers() {
        listner=new CustomOnclickListner();
        btn_login.setOnClickListener(listner);
        eyes1.setOnClickListener(listner);
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
                    finish();
                    break;
                case R.id.eyes1:
                    if(isHideFirst==true){
                        eyes1.setImageResource(R.drawable.openeye);
                        HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();
                        user_pwd1.setTransformationMethod(method);
                        isHideFirst=false;
                    }else{
                        eyes1.setImageResource(R.drawable.closeeye);
                        TransformationMethod method1= PasswordTransformationMethod.getInstance();
                        user_pwd1.setTransformationMethod(method1);
                        isHideFirst=true;
                    }
                    //光标的位置
                    int index=user_pwd1.getText().toString().length();
                    user_pwd1.setSelection(index);
                    break;
            }
            }
        }
}
