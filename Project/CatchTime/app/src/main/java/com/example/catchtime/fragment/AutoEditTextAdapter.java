package com.example.catchtime.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.catchtime.R;

import java.util.ArrayList;
import java.util.List;

public class AutoEditTextAdapter extends BaseAdapter implements Filterable {
    private ArrayFilter mFilter;
    private List<String> mList;
    private List<String> mList2;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> mUnfilteredData;

    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }

    public AutoEditTextAdapter(List<String> mList, List<String> mList2, Context context) {
        this.mList = mList;
        this.context = context;
        this.mList2 = mList2;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_auto, null);

            holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(R.id.name);
            holder.text2=view.findViewById(R.id.address);
            holder.dianji=view.findViewById(R.id.dianji);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
//item的点击事件
        if( mOnItemClickListener!= null){
            holder.dianji.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.dianji.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
        String pc = mList.get(position);
        String pc2=mList2.get(position);

        holder.text1.setText(pc);
        holder.text2.setText(pc2);


        return view;
    }

    static class ViewHolder {
        public TextView text1;
        public TextView text2;
        public LinearLayout dianji;

    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }
    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<String>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<String> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<String> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    String pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if (pc != null && pc.startsWith(prefixString)) {

                            newValues.add(pc);
                        } else if (pc != null && pc.startsWith(prefixString)) {

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            mList = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
