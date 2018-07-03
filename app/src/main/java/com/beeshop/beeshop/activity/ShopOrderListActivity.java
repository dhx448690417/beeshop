package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyOrderListAdapter;
import com.beeshop.beeshop.adapter.ShopOrderListAdapter;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.SearchShopEntity;
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
 * Time：  2018/5/20 下午12:53
 * Description：产品订单列表
 */
public class ShopOrderListActivity extends BaseListActivity<OrderListEntity.ListBean>  {

    private ShopOrderListAdapter mShopOrderListAdapter;
    private int mCurrentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("产品订单");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mShopOrderListAdapter = new ShopOrderListAdapter(this,mList);
        return mShopOrderListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();

        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);
                mList.clear();
                mCurrentPage = 1;
                getShopOrderList();
            }
        });
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);

                getShopOrderList();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取店铺订单别表
     */
    private void getShopOrderList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("page", mCurrentPage);
        HttpLoader.getInstance().getShopOrderList(params1, mCompositeSubscription, new SubscriberCallBack<OrderListEntity>(this, this) {


            @Override
            protected void onSuccess(OrderListEntity response) {
                super.onSuccess(response);

                if (response.getList().size() > 0) {
                    mCurrentPage++;
                    mList.addAll(response.getList());
                    mShopOrderListAdapter.notifyDataSetChanged();
                    hideNoContentView();
                } else {
                    showNoContentView();
                }

            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mSrlRefresh.finishRefresh();
                mSrlRefresh.finishLoadMore();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

        });
    }
}
