package com.beeshop.beeshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.SearchShopActivity;
import com.beeshop.beeshop.activity.ShopDetailActivity;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.adapter.OnRecycleItemClickListener;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author : cooper
 * Time :  2018/5/4 下午3:27
 * Description : 首页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    Unbinder unbinder;

    private List<String> mShopList = new ArrayList<>();
    private HomeShopAdapter mHomeShopAdapter;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initView() {
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHomeShopAdapter = new HomeShopAdapter(getActivity(), mShopList);
        rvHome.setLayoutManager(linearLayoutManager);
        rvHome.setAdapter(mHomeShopAdapter);
        mHomeShopAdapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), ShopDetailActivity.class));
            }
        });

        srlHome.setRefreshHeader(new DeliveryHeader(getActivity()));
        srlHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlHome.finishRefresh(2000);
            }
        });
        srlHome.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlHome.finishLoadMore(2000);
            }
        });

    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), SearchShopActivity.class));
    }
}
