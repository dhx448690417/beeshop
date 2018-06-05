package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyToolsAdapter;
import com.beeshop.beeshop.adapter.VipTypeAdapter;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.VipTypeEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class VipTypeActivity extends BaseListActivity<VipTypeEntity.ListBean> {

    private VipTypeAdapter mVipTypeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("会员分类");
        setRightText("添加会员分类");
        setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VipTypeActivity.this,VipTypeUpdateActivity.class));
            }
        });
        getVipTypeList();
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mSrlRefresh.setEnableLoadMore(false);
        mSrlRefresh.setEnableRefresh(false);
        mVipTypeAdapter = new VipTypeAdapter(this,mList);
        mVipTypeAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        return mVipTypeAdapter;
    }

    @Override
    protected void reFrensh() {
        super.reFrensh();
        getVipTypeList();
    }

    private void getVipTypeList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        HttpLoader.getInstance().getVipTypeList(params1, mCompositeSubscription, new SubscriberCallBack<VipTypeEntity>(this,this){

            @Override
            protected void onSuccess(VipTypeEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mList.addAll(response.getList());
                    mVipTypeAdapter.notifyDataSetChanged();
                    hideNoContentView();
                } else {
                    showNoContentView();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    showNoContentView();
                }
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }
}
