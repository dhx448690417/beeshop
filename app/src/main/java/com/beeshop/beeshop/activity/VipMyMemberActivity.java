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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
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

    protected List<String> mVipTypeList = new ArrayList<>();
    protected List<String> mVipMemberList = new ArrayList<>();
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

        mVipTypeList.add("");
        mVipTypeList.add("");
        mVipTypeList.add("");
        mVipTypeList.add("");
        mVipTypeList.add("");
        mVipMemberLeftAdapter = new VipMemberLeftAdapter(this, mVipTypeList);

        mVipMemberList.add("");
        mVipMemberList.add("");
        mVipMemberList.add("");
        mVipMemberList.add("");
        mVipMemberList.add("");
        mVipMemberList.add("");
        mVipMemberRightAdapter = new VipMemberRightAdapter(this, mVipMemberList);
        mVipMemberRightAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(VipMyMemberActivity.this, VipDetailActivity.class);
                startActivity(intent);
            }
        });
        rvVipType.setAdapter(mVipMemberLeftAdapter);
        rvVipMember.setAdapter(mVipMemberRightAdapter);
    }
}
