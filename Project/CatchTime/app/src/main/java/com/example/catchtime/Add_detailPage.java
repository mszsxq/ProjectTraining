package com.example.catchtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Add_detailPage extends AppCompatActivity implements View.OnClickListener {
    private TextView btnbegin;
    private TextView btnover;
    private Calendar cal;
    private int hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_detail_page);
        getbeginDate();
        btnbegin=(TextView) findViewById(R.id.tx_begin);
        btnbegin.setOnClickListener(this);

        //获取当前日期  结束
        getoverDate();
        btnover=(TextView) findViewById(R.id.tx_end);
        btnover.setOnClickListener(this);
    }

    private void getoverDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }

    private void getbeginDate() {
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_begin:
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        if(hour<10 && min<10){
                            btnbegin.setText("0"+hour+":"+"0"+min);
                        }else if (hour<10){
                            btnbegin.setText("0"+hour+":"+min);
                        }else if (min<10){
                            btnbegin.setText(hour+":"+"0"+min);
                        }else {
                            btnbegin.setText(hour+":"+min);
                        }
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(Add_detailPage.this,TimePickerDialog.THEME_HOLO_LIGHT,listener,hour,min,true);
                dialog.show();
                break;
            case R.id.tx_end:
                TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        if(hour<10 && min<10){
                            btnover.setText("0"+hour+":"+"0"+min);
                        }else if (hour<10){
                            btnover.setText("0"+hour+":"+min);
                        }else if (min<10){
                            btnover.setText(hour+":"+"0"+min);
                        }else {
                            btnover.setText(hour+":"+min);
                        }
                    }
                };
                TimePickerDialog dialog1 = new TimePickerDialog(Add_detailPage.this,TimePickerDialog.THEME_HOLO_LIGHT,listener1,hour,min,true);
                dialog1.show();
                break;
            default:
                break;
        }
    }
}
