package com.example.catchtime;

import android.os.Bundle;
import android.widget.ListView;


public class AppTimeActivity extends BaseActivity {
    private ListView mRecyclerView;
    private UseTimeAdapter mUseTimeAdapter;
    private UseTimeDataManager mUseTimeDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mRecyclerView =  findViewById(R.id.rv_show_statistics);

        mUseTimeDataManager = UseTimeDataManager.getInstance(getApplicationContext());
        mUseTimeDataManager.refreshData();
        mUseTimeAdapter = new UseTimeAdapter(this,mUseTimeDataManager.getmPackageInfoListOrderByTime(), R.layout.used_time_item_layout);
        mRecyclerView.setAdapter(mUseTimeAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
