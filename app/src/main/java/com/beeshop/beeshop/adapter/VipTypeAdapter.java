package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.VipTypeEntity;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：会员分类
 */
public class VipTypeAdapter extends RvBaseAdapter<VipTypeEntity.ListBean,VipTypeAdapter.VipTypeViewHolder> {

    public VipTypeAdapter(Context mContext, List<VipTypeEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public VipTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VipTypeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_vip_type,parent,false));
    }

    @Override
    public void onBindViewHolder(VipTypeViewHolder holder, final int position) {
        VipTypeEntity.ListBean listBean = mList.get(position);
        holder.tv_type_name.setText(listBean.getTitle());
        holder.tv_type_detail.setText(listBean.getDescribe());
        holder.rl_vip_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class VipTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type_name;
        TextView tv_type_detail;
        RelativeLayout rl_vip_type;
        ImageView iv_vip_type;
        public VipTypeViewHolder(View itemView) {
            super(itemView);
            tv_type_name = itemView.findViewById(R.id.tv_type_name);
            tv_type_detail = itemView.findViewById(R.id.tv_type_detail);
            iv_vip_type = itemView.findViewById(R.id.iv_vip_type);
            rl_vip_type = itemView.findViewById(R.id.rl_vip_type);
        }
    }
}
