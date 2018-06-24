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
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：广播
 */
public class BroadcastAdapter extends RvBaseAdapter<BroadcastListEntity.ListBean,BroadcastAdapter.BroadcatViewHolder> {


    public BroadcastAdapter(Context mContext, List<BroadcastListEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public BroadcatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BroadcatViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_broadcast,parent,false));
    }

    @Override
    public void onBindViewHolder(BroadcatViewHolder holder, final int position) {
        BroadcastListEntity.ListBean listBean = mList.get(position);
        holder.tv_bc_title.setText(listBean.getTitle());
        holder.tv_bc_content.setText(listBean.getDescribe());
        Glide.with(mContext).load(listBean.getImg())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(holder.iv_broadcast_pic);
        holder.rl_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class BroadcatViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_broadcast_pic;
        public TextView tv_bc_title;
        public TextView tv_bc_content;
        public RelativeLayout rl_broadcast;
        public BroadcatViewHolder(View itemView) {
            super(itemView);
            iv_broadcast_pic = itemView.findViewById(R.id.iv_broadcast_pic);
            tv_bc_title = itemView.findViewById(R.id.tv_bc_title);
            tv_bc_content = itemView.findViewById(R.id.tv_bc_content);
            rl_broadcast = itemView.findViewById(R.id.rl_broadcast);
        }
    }
}
