package com.beeshop.beeshop.fragment;

import android.content.Intent;
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
import com.beeshop.beeshop.activity.VipMyMemberActivity;
import com.beeshop.beeshop.activity.ProductManagerActivity;
import com.beeshop.beeshop.activity.ShopManagerActivity;
import com.beeshop.beeshop.activity.VipTypeActivity;
import com.beeshop.beeshop.adapter.ClientChatAdapter;
import com.beeshop.beeshop.model.ClientChatEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
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

    private List<ClientChatEntity.ListBean> mChatList = new ArrayList<>();
    private ClientChatAdapter mClientChatAdapter;

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
                startActivity(new Intent(getActivity(), VipMyMemberActivity.class));
                break;
            case R.id.vip_kind_manage:
                startActivity(new Intent(getActivity(), VipTypeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mClientChatAdapter = new ClientChatAdapter(getActivity(), mChatList);
        rvHome.setLayoutManager(linearLayoutManager);
        rvHome.setAdapter(mClientChatAdapter);

        srlHome.setRefreshHeader(new DeliveryHeader(getActivity()));
        srlHome.setEnableLoadMore(false);
        srlHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mChatList.clear();
                getChat();
            }
        });
        srlHome.autoRefresh();
    }


    private void getChat() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        HttpLoader.getInstance().getChat(params1, mCompositeSubscription, new SubscriberCallBack<ClientChatEntity>() {

            @Override
            protected void onSuccess(ClientChatEntity response) {
                super.onSuccess(response);
                mChatList.addAll(response.getList());
                mClientChatAdapter.notifyDataSetChanged();
                srlHome.finishRefresh();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                LogUtil.e(errorBean.getMsg());
            }

        });
    }


}
