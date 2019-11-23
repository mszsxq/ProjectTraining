package com.example.catchtime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.catchtime.R;
import com.example.catchtime.setting.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private ImageView userImg;
    private ImageView activityImg;
    private ImageView settingImg;
    private ImageView helpImg;
    private customListener listener;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.settingfragment,null);
        getViews();
        onLisener();
        return view;
    }

    private void onLisener() {
        listener = new customListener();
        userImg.setOnClickListener(listener);
        activityImg.setOnClickListener(listener);
        settingImg.setOnClickListener(listener);
        helpImg.setOnClickListener(listener);
    }

    private void getViews() {
        userImg=view.findViewById(R.id.uesr);
        activityImg = view.findViewById(R.id.add_activity);
        settingImg = view.findViewById(R.id.setting);
        helpImg = view.findViewById(R.id.help);
    }

    class customListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.uesr:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), user.class);
                    startActivity(intent);
                    break;
                case R.id.add_activity:
                    break;
                case R.id.setting:
                    break;
                case R.id.help:
                    break;
            }
        }
    }
}
