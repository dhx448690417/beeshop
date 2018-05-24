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
 * Time：  2018/5/19 上午10:49
 * Description：我的会员左侧
 */
public class VipMemberLeftAdapter extends RvBaseAdapter<String,VipMemberLeftAdapter.MyToolsViewHolder> {


    public VipMemberLeftAdapter(Context mContext, List<String> mList) {
        super(mContext, mList);
    }

    @Override
    public MyToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyToolsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_vip_member_left,parent,false));
    }

    @Override
    public void onBindViewHolder(MyToolsViewHolder holder, final int position) {
        holder.tv_vip_type_name.setText("一级会员");
        holder.rl_vip_member_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class MyToolsViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_vip_type_name;
        public TextView tv_tool_state;
        public RelativeLayout rl_vip_member_left;
        public MyToolsViewHolder(View itemView) {
            super(itemView);
            tv_vip_type_name = itemView.findViewById(R.id.tv_vip_type_name);
            rl_vip_member_left = itemView.findViewById(R.id.rl_vip_member_left);
        }
    }
}
