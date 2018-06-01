package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.BroadcastAdapter;
import com.beeshop.beeshop.adapter.ItemOnClickListener;

/**
 * Author : cooper
 * Time :  2018/5/22 上午11:26
 * Description : 附近的广播
 */
public class NearbyBroadcastActivity extends BaseListActivity<String> {

    private BroadcastAdapter mBroadcastAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("附近的广播");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
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
        mBroadcastAdapter = new BroadcastAdapter(this,mList);
        mBroadcastAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NearbyBroadcastActivity.this, BroadcastDetailActivity.class);
                startActivity(intent);
            }
        });
        return mBroadcastAdapter;
    }

    @Override
    protected void initView() {
        super.initView();

    }

}
