package com.example.catchtime.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.catchtime.Add_detailPage;
import com.example.catchtime.R;
import com.example.catchtime.activity.AddActivity;
import com.example.catchtime.setting.UserInfor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private customListener listener;
    private View view;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        relativeLayout1.setOnClickListener(listener);
        relativeLayout2.setOnClickListener(listener);
        relativeLayout3.setOnClickListener(listener);
        relativeLayout4.setOnClickListener(listener);
    }

    private void getViews() {
        relativeLayout1 = (RelativeLayout) view.findViewById(R.id.person);
        relativeLayout2 =(RelativeLayout) view.findViewById(R.id.addactivity);
        relativeLayout3 =(RelativeLayout) view.findViewById(R.id.settings);
        relativeLayout4 =(RelativeLayout) view.findViewById(R.id.helping);
    }

    class customListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.person:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserInfor.class);
                    startActivity(intent);
                    break;
                case R.id.addactivity:
                    Intent intent1=new Intent(view.getContext(), AddActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.settings:
                    Intent intent2=new Intent(view.getContext(), Add_detailPage.class);
                    startActivity(intent2);
                    break;
                case R.id.helping:
                    break;
            }
        }
    }
}
