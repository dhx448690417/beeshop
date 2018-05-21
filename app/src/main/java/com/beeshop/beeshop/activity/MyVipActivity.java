package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.MyVipAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：cooper
 * Time：  2018/5/19 上午9:16
 * Description：我加入的会员
 */
public class MyVipActivity extends BaseActivity {

    @BindView(R.id.rv_my_vip)
    RecyclerView rvMyVip;

    private MyVipAdapter myVipAdapter;
    private List<String> mMyVipList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_vip);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("我加入的会员");

        initView();
    }

    private void initView() {
        mMyVipList.add("");
        mMyVipList.add("");
        mMyVipList.add("");
        mMyVipList.add("");
        mMyVipList.add("");
        mMyVipList.add("");
        myVipAdapter = new MyVipAdapter(this, mMyVipList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyVip.setLayoutManager(linearLayoutManager);
        rvMyVip.setAdapter(myVipAdapter);
    }
}
