package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.BroadcastAdapter;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author : cooper
 * Time :  2018/5/22 上午11:26
 * Description : 我的广播
 */
public class MyBroadcastActivity extends BaseListActivity<BroadcastListEntity.ListBean> {

    private BroadcastAdapter mBroadcastAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的广播");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mBroadcastAdapter = new BroadcastAdapter(this,mList);
        mBroadcastAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                BroadcastListEntity.ListBean listBean = mList.get(position);
                Intent intent = new Intent(MyBroadcastActivity.this, BroadcastDetailActivity.class);
                intent.putExtra(BroadcastDetailActivity.PARAM_BROADCAST_ENTITY, listBean);
                startActivity(intent);
            }
        });
        return mBroadcastAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyBroadcastList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取我的广播
     */
    private void getMyBroadcastList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyBroadcastList(params1, mCompositeSubscription, new SubscriberCallBack<BroadcastListEntity>() {

            @Override
            protected void onSuccess(BroadcastListEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    hideNoContentView();
                    mList.clear();
                    mList.addAll(response.getList());
                    mBroadcastAdapter.notifyDataSetChanged();
                } else {
                    showNoContentView();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mSrlRefresh.finishRefresh();
            }

        });
    }
}
