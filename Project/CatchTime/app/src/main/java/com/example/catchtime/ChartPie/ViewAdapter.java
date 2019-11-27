package com.example.catchtime.ChartPie;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.catchtime.ChartpieFragments.TestFragment;
import com.example.catchtime.R;
import com.example.catchtime.chart.InitPieChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ViewAdapter extends FragmentPagerAdapter {

    protected static final int[] CONTENT = new int[] {
            R.drawable.flower2,
            R.drawable.flower2,
            R.drawable.flower,
            R.drawable.flower,
            R.drawable.flower2,
            R.drawable.flower,
            R.drawable.flower };

    public ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        boolean isLastPic = false;
        if (position == CONTENT.length - 1)
            isLastPic = true;
        return TestFragment.newInstance(CONTENT[position], isLastPic);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }


}
