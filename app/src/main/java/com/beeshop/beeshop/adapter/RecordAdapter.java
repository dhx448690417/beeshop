package com.beeshop.beeshop.adapter;

import android.content.Context;

import com.beeshop.beeshop.R;

import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/24 下午5:05
 * Description :
 */
public class RecordAdapter extends HolderAdapter<String> {

    public RecordAdapter(Context context, List<String> dateList) {
        super(context, dateList);
    }

    @Override
    public int buildLayoutView() {
        return R.layout.item_record;
    }

    @Override
    public void buildViewData(Context context, ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_record_title,"2018-5-24 消费 250元  状态：已消费");
        holder.setText(R.id.tv_record_explain,"削了一个脑袋。。。");
    }
}
