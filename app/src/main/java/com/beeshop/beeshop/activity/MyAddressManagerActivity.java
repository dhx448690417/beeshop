package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.AddressManagerAdapter;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.model.AddressEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：编辑收货地址
 */
public class MyAddressManagerActivity extends BaseListActivity<AddressEntity.ListBean>  {

    private TextView mTvAddProduct;
    private AddressManagerAdapter mAddressManagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("编辑收货地址");
    }

    @Override
    protected int getContentView() {
        return R.layout.aty_address_manager;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAddressManagerAdapter = new AddressManagerAdapter(this,mList);
        mAddressManagerAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                AddressEntity.ListBean listBean = mList.get(position);
                Intent intent = new Intent(MyAddressManagerActivity.this, AddressEditActivity.class);
                intent.putExtra(AddressEditActivity.PARAM_ADDRESS_INFO,listBean);
                startActivity(intent);
            }
        });
        return mAddressManagerAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvAddProduct = findViewById(R.id.tv_add_address);
        mTvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAddressManagerActivity.this,AddressEditActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMyAddress();
    }

    /**
     * 获取我的地址列表
     */
    private void getMyAddress() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyAddress(params1, mCompositeSubscription, new SubscriberCallBack<AddressEntity>(this, this) {

            @Override
            protected void onSuccess(AddressEntity response) {
                super.onSuccess(response);
                mList.clear();
                mList.addAll(response.getList());
                mAddressManagerAdapter.notifyDataSetChanged();
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
