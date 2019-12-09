package com.example.catchtime.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.fragment.MyAdapterLocation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DefaultAddress extends AppCompatActivity {

    private String[] courseNames;
    private ListView listView;
    private MyAdapterLocation myAdapterDefault;
    private String count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultaddressselection);

        Intent intent = getIntent();
        count = intent.getStringExtra("id");
        Log.e("mmy",count);

        String[] courseName = {"宿舍","学院","食堂"};
        courseNames = courseName;
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,//环境上下文
                android.R.layout.simple_list_item_1,//item布局文件的资源id
                android.R.id.text1,//显示内容的TextView的id
                courseNames);//显示的数据

        listView = findViewById(R.id.def_lv);
        listView.setAdapter(arrayAdapter);
        MyItemClickListener myItemClickListener = new MyItemClickListener();
        listView.setOnItemClickListener(myItemClickListener);
        Button button = findViewById(R.id.def_tv_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
    public class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        //点击列表中每一项都会调用该方法
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //输出课程名称
            //position位置序号
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),AddDefaultAddress.class);
            intent.putExtra("name",courseNames[position]);
            intent.putExtra("id",count);
            startActivity(intent);
        }
    }
}
