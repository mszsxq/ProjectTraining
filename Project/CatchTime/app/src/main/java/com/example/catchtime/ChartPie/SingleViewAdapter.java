package com.example.catchtime.ChartPie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.catchtime.ChartpieFragments.TestFragment;
import com.example.catchtime.R;

public class SingleViewAdapter extends FragmentPagerAdapter {
    protected static final int[] CONTENT = new int[] {
            R.drawable.flower2,
           };

    public SingleViewAdapter(FragmentManager fm) {
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
}
