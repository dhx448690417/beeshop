package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.VipEntity;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：我的会员right
 */
public class VipMemberRightAdapter extends RvBaseAdapter<VipEntity.ListBean,VipMemberRightAdapter.MyToolsViewHolder> {


    public VipMemberRightAdapter(Context mContext, List<VipEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public MyToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyToolsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_vip_member_right,parent,false));
    }

    @Override
    public void onBindViewHolder(MyToolsViewHolder holder, final int position) {
        VipEntity.ListBean listBean = mList.get(position);
        holder.tv_vip_member_name.setText(listBean.getPhone());
        holder.tv_money.setText("剩余："+listBean.getMoney()+"元");
        holder.rl_vip_member_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class MyToolsViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_vip_member_name;
        public TextView tv_money;
        public RelativeLayout rl_vip_member_right;
        public MyToolsViewHolder(View itemView) {
            super(itemView);
            tv_vip_member_name = itemView.findViewById(R.id.tv_vip_member_name);
            tv_money = itemView.findViewById(R.id.tv_money);
            rl_vip_member_right = itemView.findViewById(R.id.rl_vip_member_right);
        }
    }
}
