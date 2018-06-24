package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.adapter.OnRecycleItemClickListener;
import com.beeshop.beeshop.model.OrderListEntity;
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
 * Author：cooper
 * Time：  2018/5/20 上午11:38
 * Description：店铺排行
 */
public class ShopSortActivity extends BaseListActivity<Shop.ListBean> {

    private HomeShopAdapter mHomeShopAdapter;
    private int mPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("店铺排行");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mHomeShopAdapter = new HomeShopAdapter(this,mList);
        mHomeShopAdapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(ShopDetailActivity.PARAM_SHOP_ID, mList.get(position).getId(), ShopSortActivity.this, ShopDetailActivity.class);
            }
        });
        return mHomeShopAdapter;
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
                getShopSortList();
            }
        });
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getShopSortList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取店铺订单别表
     */
    private void getShopSortList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("page", mPage);
        HttpLoader.getInstance().getShopSortList(params1, mCompositeSubscription, new SubscriberCallBack<Shop>() {

            @Override
            protected void onSuccess(Shop response) {
                super.onSuccess(response);
                if (mPage == 1) {
                    mList.clear();
                }
                mPage++;
                mList.addAll(response.getList());
                mHomeShopAdapter.notifyDataSetChanged();
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
