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
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopDetailEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/6 下午12:06
 * Description : 首页店铺列表
 */
public class ShopDetailProductAdapter extends RecyclerView.Adapter<ShopDetailProductAdapter.HomeShopViewHolder>{

    private Context mContext;
    private List<ShopDetailEntity.ProductBean> mShopList;
    private OnRecycleItemClickListener onRecycleItemClickListener;

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener onRecycleItemClickListener) {
        this.onRecycleItemClickListener = onRecycleItemClickListener;
    }

    public ShopDetailProductAdapter(Context mContext, List<ShopDetailEntity.ProductBean> mShopList) {
        this.mContext = mContext;
        this.mShopList = mShopList;
    }

    @Override
    public HomeShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new HomeShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeShopViewHolder holder, final int position) {
        ShopDetailEntity.ProductBean listBean = mShopList.get(position);
        Glide.with(mContext).load("http://img0.imgtn.bdimg.com/it/u=137912730,2043931548&fm=27&gp=0.jpg").into(holder.iv_shop);

        holder.tv_shop_name.setText(listBean.getTitle());
        holder.tv_shop_detail.setText(listBean.getDetails());
        holder.rl_shop.setOnClickListener(new View.OnClickListener() {
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
        return mShopList.size();
    }

    class HomeShopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_shop_name;
        TextView tv_shop_detail;
        RelativeLayout rl_shop;
        ImageView iv_shop;
        public HomeShopViewHolder(View itemView) {
            super(itemView);
            tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
            tv_shop_detail = itemView.findViewById(R.id.tv_shop_detail);
            iv_shop = itemView.findViewById(R.id.iv_shop);
            rl_shop = itemView.findViewById(R.id.rl_shop);
        }
    }
}
