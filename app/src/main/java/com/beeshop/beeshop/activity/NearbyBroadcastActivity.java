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
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author : cooper
 * Time :  2018/5/22 上午11:26
 * Description : 附近的广播
 */
public class NearbyBroadcastActivity extends BaseListActivity<BroadcastListEntity.ListBean> {

    private BroadcastAdapter mBroadcastAdapter;
    private int mPage = 1;

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
        mBroadcastAdapter = new BroadcastAdapter(this,mList);
        mBroadcastAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                BroadcastListEntity.ListBean listBean = mList.get(position);
                Intent intent = new Intent(NearbyBroadcastActivity.this, BroadcastDetailActivity.class);
                intent.putExtra(BroadcastDetailActivity.PARAM_BROADCAST_ENTITY,listBean);
                startActivity(intent);
            }
        });
        return mBroadcastAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrlRefresh.setEnableRefresh(true);
        mSrlRefresh.setEnableLoadMore(true);
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                getNearbyBroadcastList();
            }
        });
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getNearbyBroadcastList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取附近广播
     */
    private void getNearbyBroadcastList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("page", mPage);
        HttpLoader.getInstance().getNearbyBroadcastList(params1, mCompositeSubscription, new SubscriberCallBack<BroadcastListEntity>() {

            @Override
            protected void onSuccess(BroadcastListEntity response) {
                super.onSuccess(response);

                if (mPage == 1) {
                    mList.clear();
                }
                mPage++;
                mList.addAll(response.getList());
                mBroadcastAdapter.notifyDataSetChanged();

                if (mList.size() > 0) {
                    hideNoContentView();
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
                mSrlRefresh.finishLoadMore();
            }
        });
    }

}
