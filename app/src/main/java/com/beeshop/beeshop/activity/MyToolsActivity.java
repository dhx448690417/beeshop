package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.MyToolsAdapter;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class MyToolsActivity extends BaseListActivity<String> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的道具");
    }

    @Override
    protected int getContentView() {
        return R.layout.aty_my_tools;
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
        return new MyToolsAdapter(this,mList);
    }
}
