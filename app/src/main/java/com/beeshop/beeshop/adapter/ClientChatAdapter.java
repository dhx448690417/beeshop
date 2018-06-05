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
import com.beeshop.beeshop.model.ClientChatEntity;
import com.beeshop.beeshop.model.Shop;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/6 下午12:06
 * Description : 首页店铺列表
 */
public class ClientChatAdapter extends RecyclerView.Adapter<ClientChatAdapter.HomeShopViewHolder>{

    private Context mContext;
    private List<ClientChatEntity.ListBean> mChatList;
    private OnRecycleItemClickListener onRecycleItemClickListener;

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener onRecycleItemClickListener) {
        this.onRecycleItemClickListener = onRecycleItemClickListener;
    }

    public ClientChatAdapter(Context mContext, List<ClientChatEntity.ListBean> list) {
        this.mContext = mContext;
        this.mChatList = list;
    }

    @Override
    public HomeShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_client_chat, parent, false);
        return new HomeShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeShopViewHolder holder, final int position) {
        ClientChatEntity.ListBean listBean = mChatList.get(position);
        Glide.with(mContext).load("http://img0.imgtn.bdimg.com/it/u=4077729777,2039477845&fm=27&gp=0.jpg").into(holder.iv_head);

        holder.tv_client_name.setText("名字");
        holder.tv_chat_conent.setText(listBean.getLast_content());
        holder.rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleItemClickListener != null) {
                    onRecycleItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    class HomeShopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_client_name;
        TextView tv_chat_conent;
        RelativeLayout rl_chat;
        ImageView iv_head;
        public HomeShopViewHolder(View itemView) {
            super(itemView);
            tv_client_name = itemView.findViewById(R.id.tv_client_name);
            tv_chat_conent = itemView.findViewById(R.id.tv_chat_conent);
            iv_head = itemView.findViewById(R.id.iv_head);
            rl_chat = itemView.findViewById(R.id.rl_chat);
        }
    }
}
