package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.VipMemberLeftAdapter;
import com.beeshop.beeshop.adapter.VipMemberRightAdapter;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.VipEntity;
import com.beeshop.beeshop.model.VipTypeEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : cooper
 * Time :  2018/5/24 上午11:50
 * Description : 我的会员们
 */
public class VipMyMemberActivity extends BaseActivity {

    @BindView(R.id.rv_vip_type)
    RecyclerView rvVipType;
    @BindView(R.id.rv_vip_member)
    RecyclerView rvVipMember;

    protected List<VipTypeEntity.ListBean> mVipTypeList = new ArrayList<>();
    protected List<VipEntity.ListBean> mVipMemberList = new ArrayList<>();
    private VipMemberLeftAdapter mVipMemberLeftAdapter;
    private VipMemberRightAdapter mVipMemberRightAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_vip_member);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("我的会员");
        setRightText("添加会员");
        setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VipMyMemberActivity.this,VipAddActivity.class));
            }
        });
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVipType.setLayoutManager(linearLayoutManager);
        rvVipMember.setLayoutManager(linearLayoutManager1);

        mVipMemberLeftAdapter = new VipMemberLeftAdapter(this, mVipTypeList);
        mVipMemberLeftAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                for (int i = 0; i < mVipTypeList.size(); i++) {
                    VipTypeEntity.ListBean listBean = mVipTypeList.get(i);
                    if (position == i) {
                        listBean.setSelected(1);
                    } else {
                        listBean.setSelected(0);
                    }
                }
                mVipMemberLeftAdapter.notifyDataSetChanged();
                getVipList(mVipTypeList.get(position).getId());
            }
        });


        mVipMemberRightAdapter = new VipMemberRightAdapter(this, mVipMemberList);
        mVipMemberRightAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(VipMyMemberActivity.this, VipDetailActivity.class);
                intent.putExtra(VipDetailActivity.PARAM_KEY_VIP_MEMBER,mVipMemberList.get(position));
                startActivity(intent);
            }
        });
        rvVipType.setAdapter(mVipMemberLeftAdapter);
        rvVipMember.setAdapter(mVipMemberRightAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getVipTypeList();
    }

    private void getVipList(int memberClassId) {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        params1.put("member_class_id", memberClassId);
        HttpLoader.getInstance().getVipList(params1, mCompositeSubscription, new SubscriberCallBack<VipEntity>(this,this){

            @Override
            protected void onSuccess(VipEntity response) {
                super.onSuccess(response);
                mVipMemberList.clear();
                mVipMemberList.addAll(response.getList());
                mVipMemberRightAdapter.notifyDataSetChanged();

            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }
        });
    }

    private void getVipTypeList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        HttpLoader.getInstance().getVipTypeList(params1, mCompositeSubscription, new SubscriberCallBack<VipTypeEntity>(this, this) {

            @Override
            protected void onSuccess(VipTypeEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mVipTypeList.clear();
                    mVipTypeList.addAll(response.getList());
                    mVipTypeList.get(0).setSelected(1); // 默认第一个选中
                    getVipList(mVipTypeList.get(0).getId());
                    mVipMemberLeftAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    VipMyMemberActivity.this.finish();
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
