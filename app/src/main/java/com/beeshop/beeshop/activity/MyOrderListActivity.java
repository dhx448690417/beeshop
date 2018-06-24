package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyOrderListAdapter;
import com.beeshop.beeshop.adapter.ProductManagerAdapter;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.ProductListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：我的订单列表
 */
public class MyOrderListActivity extends BaseListActivity<OrderListEntity.ListBean>  {

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
        mMyOrderListAdapter = new MyOrderListAdapter(this,mList);
        mMyOrderListAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                OrderListEntity.ListBean listBean = mList.get(position);
//                Intent intent = new Intent(MyOrderListActivity.this, ProductEditActivity.class);
//                intent.putExtra(ProductEditActivity.PARAM_PRODUCT_INFO,listBean);
//                startActivity(intent);
            }
        });
        return mMyOrderListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyOrderList();
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
                mList.clear();
                mList.addAll(response.getList());
                mMyOrderListAdapter.notifyDataSetChanged();
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }
}
