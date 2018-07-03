package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyVipAdapter;
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.beeshop.beeshop.model.JoinedVipEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：cooper
 * Time：  2018/5/19 上午9:16
 * Description：我加入的会员
 */
public class MyVipActivity extends BaseListActivity<JoinedVipEntity.ListBean> {

    private MyVipAdapter myVipAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("我加入的会员");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        myVipAdapter = new MyVipAdapter(this, mList);
        myVipAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                JoinedVipEntity.ListBean listBean = mList.get(position);
                Intent intent = new Intent(MyVipActivity.this, MyVipDetailActivity.class);
                intent.putExtra(MyVipDetailActivity.PARAM_MY_VIP_DETAIL_ID, listBean);
                startActivity(intent);
            }
        });
        return myVipAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyJoinedVipsList();
            }
        });
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取我加入的会员列表
     */
    private void getMyJoinedVipsList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyJoinedVipsList(params1, mCompositeSubscription, new SubscriberCallBack<JoinedVipEntity>() {

            @Override
            protected void onSuccess(JoinedVipEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    hideNoContentView();
                    mList.clear();
                    mList.addAll(response.getList());
                    myVipAdapter.notifyDataSetChanged();
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
