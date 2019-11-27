package com.example.catchtime.chart;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;
import java.util.Map;

public class PerPieEntry {
    public List<PieEntry> mList;

    public void setList(List<PieEntry> list) {
        mList = list;
    }

    public List<PieEntry> getList() {
        return mList;
    }

    public PerPieEntry(){

    }

    public PerPieEntry(List<PieEntry> list) {
        mList = list;
    }
}
