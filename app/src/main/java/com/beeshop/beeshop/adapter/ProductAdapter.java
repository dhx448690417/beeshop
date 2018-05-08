package com.beeshop.beeshop.adapter;

import android.content.Context;

import com.beeshop.beeshop.R;

import java.util.List;

/**
 * Author : cooper
 * Time :  2018/5/8 下午5:40
 * Description : 产品列表
 */
public class ProductAdapter extends HolderAdapter<String> {

    private List<String> mProductList;
    public ProductAdapter(Context context, List<String> dateList) {
        super(context, dateList);
        this.mProductList = dateList;
    }

    @Override
    public int buildLayoutView() {
        return R.layout.item_home;
    }

    @Override
    public void buildViewData(Context context, ViewHolder holder, String s, int position) {

    }
}
