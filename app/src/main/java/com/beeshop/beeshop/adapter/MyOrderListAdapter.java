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
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.ProductListEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：我的订单列表Adapter
 */
public class MyOrderListAdapter extends RvBaseAdapter<OrderListEntity.ListBean,MyOrderListAdapter.MyOrderListViewHolder> {


    public MyOrderListAdapter(Context mContext, List<OrderListEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public MyOrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrderListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_tool,parent,false));
    }

    @Override
    public void onBindViewHolder(MyOrderListViewHolder holder, final int position) {
        OrderListEntity.ListBean listBean = mList.get(position);
        Glide.with(mContext).load(listBean.getCover())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(holder.iv_product_pic);
        holder.tv_product_name.setText(listBean.getTitle());

        holder.tv_order_id.setText("订单编号："+listBean.getOrder_no());
        holder.tv_order_money.setText("订单金额："+listBean.getMoney());
        // 订单状态1待支付[商家确认]2支付成功3已发货[商家确认]4订单完成[确认收货]
        String status = "";
        switch (listBean.getStatus()) {
            case 1:
                status = "待支付";
                break;
            case 2:
                status = "支付成功";
                break;
            case 3:
                status = "已发货";
                break;
            case 4:
                status = "订单完成";
                break;
        }
        holder.tv_order_status.setText("订单状态："+status);
        holder.rl_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(position);
                }
            }
        });
    }

    class MyOrderListViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_product_pic;
        public TextView tv_product_name;
        public TextView tv_order_id;
        public TextView tv_order_money;
        public TextView tv_order_status;
        public RelativeLayout rl_tool;
        public MyOrderListViewHolder(View itemView) {
            super(itemView);
            iv_product_pic = itemView.findViewById(R.id.iv_product_pic);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_order_money = itemView.findViewById(R.id.tv_order_money);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            rl_tool = itemView.findViewById(R.id.rl_tool);
        }
    }
}
