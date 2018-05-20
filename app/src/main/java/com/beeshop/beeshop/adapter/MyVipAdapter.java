package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午9:46
 * Description 我的会员adapter
 */
public class MyVipAdapter extends RecyclerView.Adapter<MyVipAdapter.MyVipViewHolder>{

    private Context mContext;
    private List<String> mMyVipList;
    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener mItemOnClickListener) {
        this.mItemOnClickListener = mItemOnClickListener;
    }

    public MyVipAdapter(Context mContext, List<String> mMyVipList) {
        this.mContext = mContext;
        this.mMyVipList = mMyVipList;
    }

    @Override
    public MyVipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_vip, parent, false);
        return new MyVipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVipViewHolder holder, final int position) {
        holder.tv_shop_name.setText("蜂店好店铺");
        holder.tv_vip_name.setText("一个特殊的会员");
        holder.rl_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMyVipList.size();
    }

    class MyVipViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_shop_name;
        public TextView tv_vip_name;
        public RelativeLayout rl_vip;
        public MyVipViewHolder(View itemView) {
            super(itemView);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_vip_name = itemView.findViewById(R.id.tv_vip_name);
            rl_vip = itemView.findViewById(R.id.rl_vip);
        }
    }
}
