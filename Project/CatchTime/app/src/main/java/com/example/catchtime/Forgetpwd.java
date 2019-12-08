package com.example.catchtime;
import android.content.Intent;
import android.os.Bundle;
//import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
//import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.lang.reflect.Field;
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
//import static cn.smssdk.SMSSDK.getVerificationCode;
//import static cn.smssdk.SMSSDK.submitVerificationCode;
//import static com.mob.wrappers.SMSSDKWrapper.getSupportedCountries;
public class Forgetpwd extends AppCompatActivity {
    private final String TAG="--Forgetpwd--";
    private TextView full;
    private EditText user_phone;
    private ImageView eyes2;
    private ImageView eyes3;
    private EditText user_pwd2;//输入密码
    private EditText user_pwd3;//再次输入密码
    private Button btn_code;
    private Button btn_update;
    private EditText code;
    private boolean tag=true;
    private String pwd2;
    private String pwd3;
    private Handler handler;
    //默认密码输入框为隐藏的
    public String phone;
    private boolean isHideFirst = true;
//    private EventHandler eh;
    private int i=60;
    private CustomOnclickListner listner;
    private final String appKey="2d4d06534acde";
    private final String appSercret="9c16982effef39c9b388528a6687592f";
//    private final String appKey="2d447922e6d83";
//    private final String appSercret="1b0cbc51ed6aeff1e94ecf5f4187cebb";
//    private Handler handlern=new Handler(){
//        public void handleMessage(Message msg){
//            switch (msg.arg1){
//                case 0:
//                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
//                    Toast.makeText(Forgetpwd.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    //获取验证码成功
//                    Toast.makeText(Forgetpwd.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
//        MobSDK.init(this,appKey,appSercret);

        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Log.e("ym",info);
                if(info.equals("更新成功")){
                    Toast.makeText(Forgetpwd.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(Forgetpwd.this, Login.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                }
            }
        };
        getviews();

//        eh=new EventHandler(){
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                if(result==SMSSDK.RESULT_COMPLETE){
//                    //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        //提交验证码成功
//                        Message msg = new Message();
//                        msg.arg1 = 0;
//                        msg.obj = data;
//                        handlern.sendMessage(msg);
//                        Log.d(TAG, "提交验证码成功");
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        Message msg = new Message();
//                        //获取验证码成功
//                        msg.arg1 = 1;
//                        msg.obj = "获取验证码成功";
//                        handlern.sendMessage(msg);
//                        Log.d(TAG, "获取验证码成功");
//                    }
//                }else{
//                    Message msg = new Message();
//                    //返回支持发送验证码的国家列表
//                    msg.arg1 = 3;
//                    msg.obj = "验证失败";
//                    handlern.sendMessage(msg);
//                    Log.d(TAG, "验证失败");
//                    ((Throwable) data).printStackTrace();
//                }
//
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调

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
        btn_code.setOnClickListener(listner);
        btn_update.setOnClickListener(listner);
    }
    private void getviews() {
        full=findViewById(R.id.full);
        user_phone=findViewById(R.id.user_phone);
        eyes2=findViewById(R.id.eyes2);
        eyes3=findViewById(R.id.eyes3);
        user_pwd2=findViewById(R.id.user_pwd2);
        user_pwd3=findViewById(R.id.user_pwd3);
        btn_code=findViewById(R.id.btn_code);
        code=findViewById(R.id.code);
        btn_update=findViewById(R.id.btn_update);
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
//                case R.id.btn_code:
//                    phone=user_phone.getText().toString().trim();
//                    if(phone.equals("")){
//                        Toast.makeText(Forgetpwd.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
//                    }else{
//                        //填写了手机号码
//                        if(isMobileNO(phone)){
//                            //如果手机号码无误，则发送验证请求
//                            btn_code.setClickable(true);
//                            changeBtnGetCode();
//                            getSupportedCountries();
//                            getVerificationCode("86",phone);
//                        }else{
//                            //手机号格式有误
//                            Toast.makeText(Forgetpwd.this,"手机号格式错误，请检查",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    break;
                case R.id.btn_update:
                    String number=code.getText().toString();
                    phone=user_phone.getText().toString().trim();
                    pwd2=user_pwd2.getText().toString().trim();
                    pwd3=user_pwd3.getText().toString().trim();
                    if(pwd2.equals(pwd3)){
                        forgetpwdnew(phone,pwd2);
                    }else{
                        Log.e("error","两次密码请输入相同的数据");
                    }

//                    if(number.equals("")){
//                        Toast.makeText(Forgetpwd.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
//                    }else{
//                        submitVerificationCode("86", phone,number);
//                        if(pwd2.equals(pwd3)){
//                            forgetpwdnew(phone,pwd2);
//                        }else{
//                            Log.e("error","两次密码请输入相同的数据");
//                        }
                        if(pwd2.equals(pwd3)){
                            forgetpwdnew(phone,pwd2);
                        }else{
                            Log.e("error","两次密码请输入相同的数据");
                        }
//                    }
                    break;
            }
        }
    }
    private void forgetpwdnew(String phone, String pwd2) {
        User user = new User(phone,pwd2);
        Gson gson = new Gson();
        String info = gson.toJson(user);
        Log.e("1","ok");
        new Thread(){
            public void run(){
                try {
                    URL url = new URL("http://192.168.43.65:8080/Catchtime/UserController?phone="+phone+"&&pwd="+pwd2);
                    Log.e("2","ok");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("ww",info);
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
//    private void changeBtnGetCode() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                if (tag) {
//                    while (i > 0) {
//                        i--;
//                        //如果活动为空
//                        if (Forgetpwd.this == null) {
//                            break;
//                        }
//                        Forgetpwd.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                btn_code.setText("获取验证码(" + i + ")");
//                                btn_code.setClickable(false);
//                            }
//                        });
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    tag = false;
//                }
//                i = 60;
//                tag = true;
//                if (Forgetpwd.this != null) {
//                    Forgetpwd.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn_code.setText("获取验证码");
//                            btn_code.setClickable(true);
//                        }
//                    });
//                }
//            }
//        };
//        thread.start();
//    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.arg1=4;
        msg.obj = info;
        handler.sendMessage(msg);
    }
//    private boolean isMobileNO(String phone) {
//        String telRegex = "[1][358]\\d{9}";
//        if (TextUtils.isEmpty(phone))
//            return false;
//        else
//            return phone.matches(telRegex);
//    }
}