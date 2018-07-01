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
import com.beeshop.beeshop.activity.MyAddressManagerActivity;
import com.beeshop.beeshop.activity.MyBroadcastActivity;
import com.beeshop.beeshop.activity.MyOrderListActivity;
import com.beeshop.beeshop.activity.MyToolsActivity;
import com.beeshop.beeshop.activity.MyVipActivity;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

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
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void initView() {
        tvUserName.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_USER_NAME, ""));
        tvUserPhone.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_PHONE, ""));
        Glide.with(getActivity()).load(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_ICON, ""))
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(ivUserHead);

        tvVersion.setText("v"+Utils.getVersion());
    }

    @Override
    public void onStart() {
        super.onStart();
        getMyInfo();
    }

    @OnClick({R.id.rl_mine_data, R.id.rl_my_vip, R.id.rl_my_order, R.id.rl_set_address, R.id.rl_my_broadcast, R.id.rl_my_tool, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_data:
                startActivity(new Intent(getActivity(), MineSettingActivity.class));
                break;
            case R.id.rl_my_vip:
                startActivity(new Intent(getActivity(), MyVipActivity.class));
                break;
            case R.id.rl_my_broadcast:
                startActivity(new Intent(getActivity(), MyBroadcastActivity.class));
                break;
            case R.id.rl_my_tool:
                startActivity(new Intent(getActivity(), MyToolsActivity.class));
                break;
            case R.id.rl_my_order:
                startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                break;
            case R.id.rl_set_address:
                startActivity(new Intent(getActivity(), MyAddressManagerActivity.class));
                break;
            case R.id.rl_setting:
                break;
        }
    }

    /**
     * 获取我的信息
     */
    private void getMyInfo() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        HttpLoader.getInstance().getMyInfo(params1, mCompositeSubscription, new SubscriberCallBack<UserEntity>() {

            @Override
            protected void onSuccess(UserEntity response) {
                super.onSuccess(response);
                SharedPreferenceUtil.putUserPreferences(SharedPreferenceUtil.KEY_ICON, response.getHeadimg());
                SharedPreferenceUtil.putUserPreferences(SharedPreferenceUtil.KEY_USER_NAME, response.getUsername());
                SharedPreferenceUtil.putUserPreferences(SharedPreferenceUtil.KEY_PHONE, response.getPhone());

                Glide.with(getActivity()).load(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_ICON, ""))
                        .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                        .into(ivUserHead);
                tvUserName.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_USER_NAME, ""));
                tvUserPhone.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_PHONE, ""));
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

}
