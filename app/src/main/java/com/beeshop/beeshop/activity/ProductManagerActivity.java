package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ProductManagerAdapter;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：
 */
public class ProductManagerActivity extends BaseListActivity<String>  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("产品管理");
    }

    @Override
    protected int getContentView() {
        return R.layout.aty_product_manager;
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
        return new ProductManagerAdapter(this,mList);
    }
}
