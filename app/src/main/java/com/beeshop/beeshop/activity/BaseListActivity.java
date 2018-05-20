package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:21
 * Description：列表 BaseActivity
 */
public abstract class BaseListActivity<T> extends BaseActivity {

    protected RecyclerView mRvList;
    protected List<T> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);

        initView();
    }

    protected void initView() {
        mRvList = this.findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(linearLayoutManager);
        mRvList.setAdapter(getAdapter());
    }

    protected abstract int getContentView();

    protected abstract RecyclerView.Adapter getAdapter();
}
