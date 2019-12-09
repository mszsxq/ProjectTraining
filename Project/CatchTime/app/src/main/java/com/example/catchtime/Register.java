package com.example.catchtime;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
//import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.catchtime.entity.User;
import com.google.gson.Gson;
//import com.mob.MobSDK;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import androidx.appcompat.app.AppCompatActivity;
//import cn.bmob.sms.BmobSMS;
//import cn.bmob.sms.exception.BmobException;
//import cn.bmob.sms.listener.RequestSMSCodeListener;
//import cn.bmob.sms.listener.VerifySMSCodeListener;
//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
//import static com.mob.wrappers.SMSSDKWrapper.getSupportedCountries;
//import static com.mob.wrappers.SMSSDKWrapper.getVerificationCode;
//import static com.mob.wrappers.SMSSDKWrapper.submitVerificationCode;
public class Register extends AppCompatActivity {
    private final String TAG = "--Register--";
    public String country = "86";
    private static final int CODE_REPEAT = 1;
    private TextView btn_login;
    private TextView btn_register;
    private EditText full_re;
    private TextView full;
    private ImageView eyes1;
    private EditText user_pwd1;
    private Button countdown;
    private Button register;
    private EditText et;
    private String phone;
    private String password;
    private Handler handler;
    private boolean tag = true;
    private int i = 60;
//    private EventHandler eh;
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;
    private CustomOnclickListner listner;
//    private Handler handler1 = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.arg1) {
//                case 0:
//                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
//                    Toast.makeText(Register.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    //获取验证码成功
//                    Toast.makeText(Register.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Log.e("mmy", info);
                if (info.equals("注册成功")) {
                    Intent intent = new Intent();
                    intent.setClass(Register.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in, R.anim.out);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                }
            }
        };
        //MobSDK.init(this, "2d447922e6d83", "1b0cbc51ed6aeff1e94ecf5f4187cebb");
//        MobSDK.init(this, "2d447922e6d83", "1b0cbc51ed6aeff1e94ecf5f4187cebb");
        getviews();
        registers();
//        eh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        //提交验证码成功
//                        Message msg = new Message();
//                        msg.arg1 = 0;
//                        msg.obj = data;
//                        handler1.sendMessage(msg);
//                        Log.d(TAG, "提交验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        Message msg = new Message();
//                        //获取验证码成功
//                        msg.arg1 = 1;
//                        msg.obj = "获取验证码成功";
//                        handler1.sendMessage(msg);
//                        Log.d(TAG, "获取验证码成功");
//                    }
//                } else {
//                    Message msg = new Message();
//                    //返回支持发送验证码的国家列表
//                    msg.arg1 = 3;
//                    msg.obj = "验证失败";
//                    handler1.sendMessage(msg);
//                    Log.d(TAG, "验证失败");
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调
        // 监听号码输入框的字数
        full_re.addTextChangedListener(new TextWatcher() {
            CharSequence input;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input = s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                full.setText(String.format("%d/11", input.length()));
                if (s.toString().trim().length() > 11) {
                    full_re.setTextColor(getResources().getColor(R.color.red));
                    full.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else {
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
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        full_re = findViewById(R.id.full_re);
        full = findViewById(R.id.full);
        user_pwd1 = findViewById(R.id.user_pwd1);
        eyes1 = findViewById(R.id.eyes1);
        register = findViewById(R.id.re_btn_register);
        countdown = findViewById(R.id.re_btn_countdown);
        et = findViewById(R.id.re_et);
    }

    private void registers() {
        listner = new CustomOnclickListner();
        btn_login.setOnClickListener(listner);
        eyes1.setOnClickListener(listner);
        register.setOnClickListener(listner);
        countdown.setOnClickListener(listner);
    }

    class CustomOnclickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    Intent intent = new Intent();
                    intent.setClass(Register.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in, R.anim.out);
                    finish();
                    break;
//                case R.id.re_btn_countdown:
//                    phone = full_re.getText().toString();
//                    if (phone.equals("")) {
//                        Toast.makeText(Register.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
//                    } else {
//                        //填写了手机号码
//                        if (isMobileNO(phone)) {
//                            //如果手机号码无误，则发送验证请求
//                            countdown.setClickable(true);
//                            changeBtnGetCode();
//                            getSupportedCountries();
//                            getVerificationCode("86", phone);
//                        } else {
//                            //手机号格式有误
//                            Toast.makeText(Register.this, "手机号格式错误，请检查", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    break;
                case R.id.eyes1:
                    if (isHideFirst == true) {
                        eyes1.setImageResource(R.drawable.openeye);
                        HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                        user_pwd1.setTransformationMethod(method);
                        isHideFirst = false;
                    } else {
                        eyes1.setImageResource(R.drawable.closeeye);
                        TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                        user_pwd1.setTransformationMethod(method1);
                        isHideFirst = true;
                    }
                    //光标的位置
                    int index = user_pwd1.getText().toString().length();
                    user_pwd1.setSelection(index);
                    break;
                case R.id.re_btn_register:
                    phone = full_re.getText().toString();
                    password = user_pwd1.getText().toString();
                    String number = et.getText().toString();
                    RegisterUser(phone, password);
//                    if (number.equals("")){
//                        Toast.makeText(Register.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
//                    }else {
//                        submitVerificationCode("86", phone, number);
//                        RegisterUser(phone, password);
//                    if (number.equals("")) {
//                        Toast.makeText(Register.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
//                    } else {
//                        submitVerificationCode("86", phone, number);
//                        RegisterUser(phone, password);
//                    }
                    break;
                }
//                    }

            }
        }

        private void RegisterUser(String phone, String password) {
            User user = new User(phone, password);
            Gson gson = new Gson();
            String client = gson.toJson(user);
            Log.e("mmy", client);
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://10.7.82.38:8080/Catchtime/UserController?client=" + client);
                        URLConnection conn = url.openConnection();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String info = reader.readLine();
                        Log.e("ww", info);
                        wrapperMessage(info);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        private void wrapperMessage(String info) {
            Message msg = Message.obtain();
            msg.obj = info;
            handler.sendMessage(msg);
        }

        private boolean isMobileNO(String sphone) {
            String telRegex = "[1][358]\\d{9}";
            if (TextUtils.isEmpty(sphone))
                return false;
            else
                return sphone.matches(telRegex);
        }

        private void changeBtnGetCode() {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    if (tag) {
                        while (i > 0) {
                            i--;
                            //如果活动为空
                            if (Register.this == null) {
                                break;
                            }
                            Register.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countdown.setText("获取验证码(" + i + ")");
                                    countdown.setClickable(false);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tag = false;
                    }
                    i = 60;
                    tag = true;
                    if (Register.this != null) {
                        Register.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                countdown.setText("获取验证码");
                                countdown.setClickable(true);
                            }
                        });
                    }
                }
            };
            thread.start();
        }
    }
