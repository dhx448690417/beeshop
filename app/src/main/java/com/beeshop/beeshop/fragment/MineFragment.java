package com.beeshop.beeshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.MineSettingActivity;
import com.beeshop.beeshop.activity.MyBroadcastActivity;
import com.beeshop.beeshop.activity.MyToolsActivity;
import com.beeshop.beeshop.activity.MyVipActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author : cooper
 * Time :  2018/5/4 下午3:27
 * Description : 我的
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.rl_mine_data)
    RelativeLayout rlMineData;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.rl_my_vip)
    RelativeLayout rlMyVip;
    @BindView(R.id.rl_my_broadcast)
    RelativeLayout rlMyBroadcast;
    @BindView(R.id.rl_my_tool)
    RelativeLayout rlMyTool;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.rl_mine_data, R.id.rl_my_vip, R.id.rl_my_broadcast, R.id.rl_my_tool, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_data:
                startActivity(new Intent(getActivity(), MineSettingActivity.class));
                break;
            case R.id.rl_my_vip:
                startActivity(new Intent(getActivity(), MyVipActivity.class));
                startActivity(new Intent(getActivity(), MyVipActivity.class));
                break;
            case R.id.rl_my_broadcast:
                startActivity(new Intent(getActivity(), MyBroadcastActivity.class));
                break;
            case R.id.rl_my_tool:
                startActivity(new Intent(getActivity(), MyToolsActivity.class));
                break;
            case R.id.rl_setting:
                break;
        }
    }
}
