package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.MyToolsAdapter;
import com.beeshop.beeshop.adapter.MyVipDetailAdapter;
import com.beeshop.beeshop.views.MaterialDialog;


/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class MyVipDetailActivity extends BaseListActivity<String> {

    private MyVipDetailAdapter mMyVipDetailAdapter;
    private MaterialDialog ConfirmDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的会员详情");
    }

    @Override
    protected int getContentView() {
        return R.layout.aty_my_vip_detail;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mMyVipDetailAdapter = new MyVipDetailAdapter(this,mList);
        mMyVipDetailAdapter.setmConfirmClickCallBack(new MyVipDetailAdapter.ConfirmClickCallBack() {
            @Override
            public void confirmClick(int postion) {
                ConfirmDialog.show();
            }
        });
        return mMyVipDetailAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        ConfirmDialog = new MaterialDialog(this)
                .setTitle("确认订单")
                .setMessage("请仔细核对订单金额及内容，确认后将从对应会员记录中扣除订单金额。")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfirmDialog.dismiss();

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfirmDialog.dismiss();

                    }
                });

    }
}
