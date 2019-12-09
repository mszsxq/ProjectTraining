package com.example.catchtime;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.example.catchtime.setting.UserInfor;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import androidx.appcompat.app.AppCompatActivity;
public class Login extends AppCompatActivity {
    private TextView btn_login;
    private TextView btn_register;
    private TextView btn_fpwd;
    private EditText user_number;
    private TextView full;
    private EditText user_pwd;
    private ImageView eyes;
    private Button btlog;
    private Handler handler;
    //默认密码输入框为隐藏的
    private boolean isHideFirst = true;
    private CustomOnclickListener listener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
        getViews();
        registers();
        //------------------

        //---------------
        btlog = findViewById(R.id.btn_log);
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        //显示服务器返回的数据
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("1","ok?");
                Log.e("info",info);
                if("密码错误".equals(info)){
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                }else{
                    Gson gson=new Gson();
                    User usering = new User();
                    Log.e("2","ok?");
                    usering = gson.fromJson(info,User.class);

                    int id=usering.getUser_id();
                    Log.e("3","ok?");
                    SharedPreferences.Editor editor=p.edit();
                    editor.putInt("user_id",id);
                    editor.commit();
                    Log.e("id",""+id);
                    Log.e("4","ok?");
                    Intent intent = new Intent(Login.this, UserInfor.class);
                    startActivity(intent);
//                    intent.putExtra("name",usering.getUsername());
//                    intent.putExtra("moto",usering.getMoto());
//                    intent.putExtra("image",usering.getImage());
//                    intent.putExtra("phone",usering.getPhone());
//                    intent.putExtra("time",usering.getRegister_date());


                }
            }
        };
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
//        btlog=findViewById(R.id.btn_log);
//        btlog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getaa();
//                sendMessage();
//            }
//        });

    }

    private void getaa() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.btn_log), "", "")
                        // All options below are optional
                        .outerCircleColor(R.color.green)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
//                        .icon()                     // Specify a custom drawable to draw as the target
                        .targetRadius(50),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
//                        doSomething();
                    }
                });
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
                   finish();
                   break;
               case R.id.btn_fpwd:
                   Intent intent1=new Intent();
                   intent1.setClass(Login.this, Forgetpwd.class);
                   startActivity(intent1);
                   break;
               case R.id.eyes://改变密码输入框是否可见
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
    //向服务器发送数据
    private void sendMessage() {
        String num = user_number.getText().toString();
        String pwd = user_pwd.getText().toString();
        User user = new User(num,pwd);
        Gson gson = new Gson();
        String client = gson.toJson(user);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.65:8080/Catchtime/LoginController?client="+client);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    if(null!=info) {
                        Log.e("ww", info);
                        wrapperMessage(info);
                    }
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
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
