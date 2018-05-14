package com.beeshop.beeshop.fragment;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
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

    private List<String> mShopList = new ArrayList<>();
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
    protected void initView() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
