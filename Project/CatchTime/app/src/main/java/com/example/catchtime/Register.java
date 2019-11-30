package com.example.catchtime;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class Register extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private EditText full_re;
    private TextView full;
    private ImageView eyes1;
    private EditText user_pwd1;
    private Button countdown;
    private Button register;
    private EditText et;
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;
    private CustomOnclickListner listner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        BmobSMS.initialize(Register.this, "c6cdff9c3ade26719c30c17eb8f38d4b");
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
        register = findViewById(R.id.re_btn_register);
        countdown = findViewById(R.id.re_btn_countdown);
        et = findViewById(R.id.re_et);
    }
    private void registers() {
        listner=new CustomOnclickListner();
        btn_login.setOnClickListener(listner);
        eyes1.setOnClickListener(listner);
        register.setOnClickListener(listner);
        countdown.setOnClickListener(listner);
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
                case R.id.re_btn_countdown:
                    // 将按钮设置为不可用状态
                    countdown.setEnabled(false);
                    // 启动倒计时的服务
                    new CountDownTimer(60000, 1000) {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countdown.setBackgroundResource(R.color.orange_dark);
                            countdown.setTextColor(R.color.white);
                            countdown.setText(millisUntilFinished / 1000 + "秒");
                        }

                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onFinish() {
                            countdown.setClickable(true);
                            countdown.setBackgroundResource(R.color.gray);
                            countdown.setTextColor(R.color.orange_dark);
                            countdown.setText("重新发送");
                            countdown.setEnabled(true);
                        }
                    }.start();
                    Log.e("MESSAGE:", "4");
                    String phone = full_re.getText().toString();
                    Log.e("mmy",phone);
                    BmobSMS.requestSMSCode(Register.this,phone, "注册验证码", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if (ex == null) {//验证码发送成功
                                Log.e("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                            }else {
                                Log.e("bmob","errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                            }
                        }
                    });
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
                case R.id.re_btn_register:
                    String number = et.getText().toString();
                    if (!TextUtils.isEmpty(number)) {
                        BmobSMS.verifySmsCode(Register.this, full_re.getText().toString(), number, new VerifySMSCodeListener() {
                            @Override
                            public void done(BmobException ex) {
                                if (ex == null) {//短信验证码已验证成功
                                    Log.e("bmob", "验证通过");
                                    Intent intent=new Intent();
                                    intent.setClass(Register.this, Login.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.in,R.anim.out);
                                    finish();
                                } else {
                                    Log.e("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                }
                            }
                        });
                    }
                    break;
            }
        }
    }
}
