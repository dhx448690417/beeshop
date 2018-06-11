package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ProductManagerAdapter;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：编辑产品
 */
public class ProductManagerActivity extends BaseListActivity<String>  {

    private TextView mTvAddProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("编辑产品");
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

    @Override
    protected void initView() {
        super.initView();
        mTvAddProduct = findViewById(R.id.tv_add_product);
        mTvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductManagerActivity.this,ProductEditActivity.class));
            }
        });
    }
}
