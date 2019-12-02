package com.example.catchtime.ChartpieFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.catchtime.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class TestFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final String KEY_ISLASTPIC = "TestFragment:IsLastPic";
    private int mContent;
    private boolean mIsLastPic;
    private View root;
    private View change;


    public static TestFragment newInstance(int content, boolean isLastPic) {
        TestFragment fragment = new TestFragment();

        fragment.mContent = content;
        fragment.mIsLastPic = isLastPic;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getInt(KEY_CONTENT);
            mIsLastPic = savedInstanceState.getBoolean(KEY_ISLASTPIC);
        }
        root = inflater
                .inflate(R.layout.accountfragment, container, false);
        change = inflater.inflate(R.layout.item_chart_pie, container, false);
        showChartPie();
        /*Button btn = (Button) root.findViewById(R.id.btn);
        if (mIsLastPic)
            btn.setVisibility(View.VISIBLE);
        else
            btn.setVisibility(View.GONE);*/
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTENT, mContent);
        outState.putBoolean(KEY_ISLASTPIC, mIsLastPic);
    }

    private void showChartPie() {
        PieChart chart = root.findViewById(R.id.piechart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieEntry pieEntry = new PieEntry((float) ((Math.random() * 5) + 5 / 5), " 测试", getResources().getDrawable(R.drawable.marker2));
            entries.add(pieEntry);
        }
    }


}

