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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class Login extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private TextView btn_fpwd;
    private EditText user_number;
    private TextView full;
    private EditText user_pwd;
    private ImageView eyes;
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;

    private CustomOnclickListener listener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getViews();
        registers();
        //监听号码输入框的字数
        user_number.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input = s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                full.setText(String.format("%d/11",input.length()));
                if(input.length()>11){
                    user_number.setTextColor(getResources().getColor(R.color.red));
                    full.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else{
                    user_number.setTextColor(getResources().getColor(R.color.black));
                    full.setTextColor(getResources().getColor(R.color.black));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void registers() {
        listener=new CustomOnclickListener();
        btn_login.setOnClickListener(listener);
        btn_register.setOnClickListener(listener);
        btn_fpwd.setOnClickListener(listener);
        user_number.setOnClickListener(listener);
        eyes.setOnClickListener(listener);

    }

    private void getViews() {
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        btn_fpwd=findViewById(R.id.btn_fpwd);
        user_number=findViewById(R.id.user_number);
        full=findViewById(R.id.full);
        user_pwd=findViewById(R.id.user_pwd);
        eyes=findViewById(R.id.eyes);
    }
   class CustomOnclickListener implements View.OnClickListener{

       @Override
       public void onClick(View v) {
           switch (v.getId()){
               case R.id.btn_register:
                   Intent intent=new Intent();
                   intent.setClass(Login.this, Register.class);
                   startActivity(intent);
                   overridePendingTransition(
                           R.anim.in,//进入动画
                           R.anim.out//出去动画
                   );
                   break;
               case R.id.btn_fpwd://改变密码输入框是否可见
                   Intent intent1=new Intent();
                   intent1.setClass(Login.this, Forgetpwd.class);
                   startActivity(intent1);
                   break;
               case R.id.eyes:
                   if(isHideFirst==true){
                       eyes.setImageResource(R.drawable.openeye);
                       HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();
                       user_pwd.setTransformationMethod(method);
                       isHideFirst=false;
                   }else{
                       eyes.setImageResource(R.drawable.closeeye);
                       TransformationMethod method1= PasswordTransformationMethod.getInstance();
                       user_pwd.setTransformationMethod(method1);
                       isHideFirst=true;
                   }
                   //光标的位置
                   int index=user_pwd.getText().toString().length();
                   user_pwd.setSelection(index);
                   break;

           }

       }
   }

}
