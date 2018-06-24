package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyOrderListAdapter;
import com.beeshop.beeshop.adapter.ShopOrderListAdapter;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：产品订单列表
 */
public class ShopOrderListActivity extends BaseListActivity<OrderListEntity.ListBean>  {

    private ShopOrderListAdapter mShopOrderListAdapter;

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
//        mShopOrderListAdapter.setmItemOnClickListener(new ItemOnClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                OrderListEntity.ListBean listBean = mList.get(position);
//                Intent intent = new Intent(MyOrderListActivity.this, ProductEditActivity.class);
//                intent.putExtra(ProductEditActivity.PARAM_PRODUCT_INFO,listBean);
//                startActivity(intent);
//            }
//        });
        return mShopOrderListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getShopOrderList();
    }

    /**
     * 获取店铺订单别表
     */
    private void getShopOrderList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getShopOrderList(params1, mCompositeSubscription, new SubscriberCallBack<OrderListEntity>(this, this) {

            @Override
            protected void onSuccess(OrderListEntity response) {
                super.onSuccess(response);
                mList.clear();
                mList.addAll(response.getList());
                mShopOrderListAdapter.notifyDataSetChanged();
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
