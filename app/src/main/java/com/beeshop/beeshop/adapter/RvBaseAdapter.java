package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:32
 * Description：RecycleView的BaseAdapter
 */
public abstract class RvBaseAdapter<V,T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected Context mContext;
    protected List<V> mList;

    protected ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener mItemOnClickListener) {
        this.mItemOnClickListener = mItemOnClickListener;
    }

    public RvBaseAdapter(Context mContext, List<V> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

}
