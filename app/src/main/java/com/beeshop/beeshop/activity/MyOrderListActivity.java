package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyOrderListAdapter;
import com.beeshop.beeshop.adapter.ProductManagerAdapter;
import com.beeshop.beeshop.model.OrderCreateEntity;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.ProductListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：我的订单列表
 */
public class MyOrderListActivity extends BaseListActivity<OrderListEntity.ListBean> {

    private MyOrderListAdapter mMyOrderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的订单");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mMyOrderListAdapter = new MyOrderListAdapter(this, mList);
        mMyOrderListAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                OrderListEntity.ListBean listBean = mList.get(position);

                if (listBean.getStatus() == 1) {
                    OrderCreateEntity orderCreateEntity = new OrderCreateEntity();

                    orderCreateEntity.setTitle(listBean.getTitle());
                    orderCreateEntity.setDetails(listBean.getRemark());
                    orderCreateEntity.setUnit(listBean.getUnit());
                    orderCreateEntity.setCover(listBean.getCover());
                    orderCreateEntity.setNum(listBean.getNum());
                    orderCreateEntity.setOrder_id(listBean.getId() + "");
                    orderCreateEntity.setOrder_no(listBean.getOrder_no());
                    orderCreateEntity.setPrice(listBean.getPrice());
                    orderCreateEntity.setReal_payment(listBean.getReal_payment());

                    Intent intent = new Intent(MyOrderListActivity.this, OrderConfirmBuyActivity.class);
                    intent.putExtra(OrderConfirmBuyActivity.PARAM_ORDER_KEY, orderCreateEntity);
                    startActivity(intent);
                }

            }
        });
        return mMyOrderListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyOrderList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取我的订单别表
     */
    private void getMyOrderList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyOrderList(params1, mCompositeSubscription, new SubscriberCallBack<OrderListEntity>(this, this) {

            @Override
            protected void onSuccess(OrderListEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mList.clear();
                    mList.addAll(response.getList());
                    mMyOrderListAdapter.notifyDataSetChanged();
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
            }

        });
    }
}
