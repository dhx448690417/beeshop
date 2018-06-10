package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.VipTypeEntity;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：我的会员左侧
 */
public class VipMemberLeftAdapter extends RvBaseAdapter<VipTypeEntity.ListBean,VipMemberLeftAdapter.MyToolsViewHolder> {


    public VipMemberLeftAdapter(Context mContext, List<VipTypeEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public MyToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyToolsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_vip_member_left,parent,false));
    }

    @Override
    public void onBindViewHolder(MyToolsViewHolder holder, final int position) {
        VipTypeEntity.ListBean listBean = mList.get(position);
        holder.tv_vip_type_name.setText(listBean.getTitle());
        holder.rl_vip_member_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
        if (listBean.getSelected() != 0) {
            holder.rl_vip_member_left.setBackgroundColor(Color.WHITE);
        } else {
            holder.rl_vip_member_left.setBackgroundColor(ContextCompat.getColor(mContext,R.color.grey_F5F5F5));
        }
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
