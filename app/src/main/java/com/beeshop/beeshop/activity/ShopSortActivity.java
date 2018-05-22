package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;

/**
 * Author：cooper
 * Time：  2018/5/20 上午11:38
 * Description：搜索结果
 */
public class SearchResultActivity extends BaseListActivity<String> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的道具");
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
        return new HomeShopAdapter(this,mList);
    }
}
