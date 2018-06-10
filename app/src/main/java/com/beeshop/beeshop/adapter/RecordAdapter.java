package com.beeshop.beeshop.adapter;

import android.content.Context;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.VipMoneyHistoryRecord;

import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/24 下午5:05
 * Description :
 */
public class RecordAdapter extends HolderAdapter<VipMoneyHistoryRecord.ListBean> {

    private int mRecordType; // 0消费   1充值
    public RecordAdapter(Context context, List<VipMoneyHistoryRecord.ListBean> dateList) {
        super(context, dateList);
    }

    @Override
    public int buildLayoutView() {
        return R.layout.item_record;
    }

    @Override
    public void buildViewData(Context context, ViewHolder holder,VipMoneyHistoryRecord.ListBean listBean, int position) {
        String status = listBean.getStatus() == 1 ? "状态：待确认" : "状态：已确认";
        String type = mRecordType == 0 ? "消费 "+listBean.getMoney()+"元 "+status:"充值 "+listBean.getMoney()+"元";
        holder.setText(R.id.tv_record_title,listBean.getCreated_time()+"  "+type);
        holder.setText(R.id.tv_record_explain,listBean.getDescribe());
    }

    public void setRecordType(int type) {
        mRecordType =type;
    }
}
