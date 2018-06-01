package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.List;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:49
 * Description：我的工具
 */
public class MyVipDetailAdapter extends RvBaseAdapter<String,MyVipDetailAdapter.MyVipDetailViewHolder> {

    private ConfirmClickCallBack mConfirmClickCallBack;

    public void setmConfirmClickCallBack(ConfirmClickCallBack mConfirmClickCallBack) {
        this.mConfirmClickCallBack = mConfirmClickCallBack;
    }

    public MyVipDetailAdapter(Context mContext, List<String> mList) {
        super(mContext, mList);
    }

    @Override
    public MyVipDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyVipDetailViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_vip_record,parent,false));
    }

    @Override
    public void onBindViewHolder(MyVipDetailViewHolder holder, final int position) {
        holder.tv_record_title.setText("交易主题");
        holder.tv_jiaoyi_money.setText("250元");
        holder.tv_jiaoyi_describe.setText("我有钱就是牛我有钱就是牛我有钱就是牛我有钱就是牛我有钱就是牛我有钱就是牛我有钱就是牛");
        holder.tv_jiaoyi_time.setText("2018-5-25 12:34");
        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmClickCallBack != null) {
                    mConfirmClickCallBack.confirmClick(position);
                }
            }
        });
    }

    class MyVipDetailViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_record_title;
        public TextView tv_jiaoyi_money;
        public TextView tv_jiaoyi_describe;
        public TextView tv_jiaoyi_time;
        public TextView tv_confirm;
        public MyVipDetailViewHolder(View itemView) {
            super(itemView);
            tv_record_title = itemView.findViewById(R.id.tv_record_title);
            tv_jiaoyi_money = itemView.findViewById(R.id.tv_jiaoyi_money);
            tv_jiaoyi_describe = itemView.findViewById(R.id.tv_jiaoyi_describe);
            tv_jiaoyi_time = itemView.findViewById(R.id.tv_jiaoyi_time);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
        }
    }

    public interface ConfirmClickCallBack{
        void confirmClick(int postion);
    }
}
