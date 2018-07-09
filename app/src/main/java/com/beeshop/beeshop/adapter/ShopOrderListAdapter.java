package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.OrderListEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：产品订单列表Adapter
 */
public class ShopOrderListAdapter extends RvBaseAdapter<OrderListEntity.ListBean,ShopOrderListAdapter.ShopOrderListViewHolder> {

    private ConfirmCallBack mConfirmCallBack;

    public void setConfirmCallBack(ConfirmCallBack confirmCallBack) {
        this.mConfirmCallBack = confirmCallBack;
    }

    public ShopOrderListAdapter(Context mContext, List<OrderListEntity.ListBean> mList) {
        super(mContext, mList);
    }

    @Override
    public ShopOrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopOrderListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shop_order_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ShopOrderListViewHolder holder, final int position) {
        final OrderListEntity.ListBean listBean = mList.get(position);
        holder.tv_record_title.setText(listBean.getTitle());
        holder.tv_jiaoyi_money.setText(listBean.getReal_payment()+"元");
        holder.tv_jiaoyi_describe.setText(listBean.getOrder_no());
        if (listBean.getCreate_time() != 0) {
            Date d = new Date(listBean.getCreate_time()*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss");
            holder.tv_jiaoyi_time.setText(sdf.format(d));
        }
        // 订单状态1待支付[商家确认]2支付成功3已发货[商家确认]4订单完成[确认收货]
        String status = "";
        switch (listBean.getStatus()) {
            case 1:
                status = "修改价格";
                holder.tv_confirm.setBackgroundResource(R.drawable.shape_red_radius_bt_bg);
                holder.tv_confirm.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                break;
            case 2:
                status = "确认发货";
                holder.tv_confirm.setBackgroundResource(R.drawable.shape_red_radius_bt_bg);
                holder.tv_confirm.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                break;
            case 3:
                status = "已发货";
                holder.tv_confirm.setBackgroundColor(Color.WHITE);
                holder.tv_confirm.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_black));
                break;
            case 4:
                status = "订单完成";
                holder.tv_confirm.setBackgroundColor(Color.WHITE);
                holder.tv_confirm.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_black));
                break;
        }
        holder.tv_confirm.setText(status);
        if (listBean.getStatus() == 1 || listBean.getStatus() == 2) {
            holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConfirmCallBack != null) {
                        mConfirmCallBack.clickConfirm(listBean.getStatus(),position);
                    }
                }
            });
        }
    }

    class ShopOrderListViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_record_title;
        public TextView tv_jiaoyi_money;
        public TextView tv_jiaoyi_describe;
        public TextView tv_jiaoyi_time;
        public TextView tv_confirm;
        public RelativeLayout rl_tool;
        public ShopOrderListViewHolder(View itemView) {
            super(itemView);
            tv_record_title = itemView.findViewById(R.id.tv_record_title);
            tv_jiaoyi_money = itemView.findViewById(R.id.tv_jiaoyi_money);
            tv_jiaoyi_describe = itemView.findViewById(R.id.tv_jiaoyi_describe);
            tv_jiaoyi_time = itemView.findViewById(R.id.tv_jiaoyi_time);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
            rl_tool = itemView.findViewById(R.id.rl_tool);
        }
    }

    /**
     * 确认回调
     */
    public interface ConfirmCallBack{
        void clickConfirm(int type,int position);
    }
}
