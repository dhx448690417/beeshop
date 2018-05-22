package com.beeshop.beeshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.ProductManagerActivity;
import com.beeshop.beeshop.activity.ShopManagerActivity;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.model.Shop;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author : cooper
 * Time :  2018/5/4 下午3:27
 * Description : 客户
 */

public class ClientFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;

    private List<Shop.ListBean> mShopList = new ArrayList<>();
    private HomeShopAdapter mHomeShopAdapter;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_client, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.client_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shop_manage:
                startActivity(new Intent(getActivity(), ShopManagerActivity.class));
                break;
            case R.id.product_manage:
                startActivity(new Intent(getActivity(), ProductManagerActivity.class));
                break;
            case R.id.vip_manage:
                break;
            case R.id.vip_kind_manage:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        Shop.ListBean listBean = new Shop.ListBean();
        mShopList.add(listBean);
        mShopList.add(listBean);
        mShopList.add(listBean);
        mShopList.add(listBean);
        mShopList.add(listBean);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHomeShopAdapter = new HomeShopAdapter(getActivity(), mShopList);
        rvHome.setLayoutManager(linearLayoutManager);
        rvHome.setAdapter(mHomeShopAdapter);

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


}
