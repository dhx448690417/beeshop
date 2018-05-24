package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyToolsAdapter;
import com.beeshop.beeshop.adapter.VipTypeAdapter;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class VipTypeActivity extends BaseListActivity<String> {

    private VipTypeAdapter mVipTypeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("会员分类");
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
        mSrlRefresh.setEnableLoadMore(false);
        mSrlRefresh.setEnableRefresh(false);
        mVipTypeAdapter = new VipTypeAdapter(this,mList);
        mVipTypeAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        return mVipTypeAdapter;
    }
}
