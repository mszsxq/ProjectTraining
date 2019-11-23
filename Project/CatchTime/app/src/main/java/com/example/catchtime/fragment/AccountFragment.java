package com.example.catchtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.catchtime.Login;
import com.example.catchtime.R;
import com.example.catchtime.activity.ActivitiesDetail;
import com.example.catchtime.activity.AddActivityDetial;
import com.roughike.bottombar.BottomBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        Log.e("test","初始化第0个页面");
        final View view=inflater.inflate(R.layout.accountfragment,null);
        Button button=view.findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ActivitiesDetail.class);
                startActivity(intent);
                BottomBar bottomBar=getActivity().findViewById(R.id.bottomBar);
                // TODO: 2019/11/18 存在问题无法进行颜色的改变 
                bottomBar.setBackgroundColor(getResources().getColor(R.color.black));
                bottomBar.setBadgeBackgroundColor(getResources().getColor(R.color.black));
            }
        });
        return view;
    }
}
