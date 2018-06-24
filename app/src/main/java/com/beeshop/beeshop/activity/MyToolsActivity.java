package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.MyToolsAdapter;
import com.beeshop.beeshop.model.BroadcastCardEntity;
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class MyToolsActivity extends BaseListActivity<BroadcastCardEntity.ListBean> {

    private MyToolsAdapter mMyToolsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我的道具");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mMyToolsAdapter = new MyToolsAdapter(this,mList);
        return mMyToolsAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrlRefresh.setEnableRefresh(true);
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyBroadcastCardList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取我的广播卡
     */
    private void getMyBroadcastCardList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyBroadcastCardList(params1, mCompositeSubscription, new SubscriberCallBack<BroadcastCardEntity>() {

            @Override
            protected void onSuccess(BroadcastCardEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    hideNoContentView();
                    mList.clear();
                    mList.addAll(response.getList());
                    mMyToolsAdapter.notifyDataSetChanged();
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
