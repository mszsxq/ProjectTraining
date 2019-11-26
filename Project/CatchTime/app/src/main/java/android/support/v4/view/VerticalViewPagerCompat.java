package android.support.v4.view;


import androidx.viewpager.widget.PagerAdapter;

public final class VerticalViewPagerCompat {
    private VerticalViewPagerCompat() {
    }

    public static class DataSetObserver extends android.database.DataSetObserver {
    }

    public static void setDataSetObserver(PagerAdapter adapter,
                                          DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }
}
