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
import com.beeshop.beeshop.activity.*;
import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.AppManager;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.utils.Utils;
import com.beeshop.beeshop.views.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tencent.bugly.beta.Beta;

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
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private MaterialDialog confirmDialog;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void initView() {
        tvUserName.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_USER_NAME, ""));
        tvUserPhone.setText(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_PHONE, ""));
        Glide.with(getActivity()).load(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_ICON, ""))
                .apply(new RequestOptions().placeholder(R.drawable.default_head).error(R.drawable.default_head).dontAnimate().centerCrop())
                .into(ivUserHead);

        tvVersion.setText("v"+Utils.getVersion());

        confirmDialog = new MaterialDialog(getActivity())
                .setTitle("退出登录")
                .setMessage("确定退出登录吗？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                        SharedPreferenceUtil.clearUserPreferences();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra(LoginActivity.PARAM_NEED_JUMP_TO_MAINACTIVITY, 1);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        getMyInfo();
    }

    @OnClick({R.id.rl_mine_data, R.id.rl_my_vip, R.id.rl_quit,R.id.rl_my_order, R.id.rl_set_address, R.id.rl_my_broadcast, R.id.rl_my_tool, R.id.rl_update})
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
            case R.id.rl_quit:
                confirmDialog.show();
                break;
            case R.id.rl_update:
                Beta.checkUpgrade();
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
                        .apply(new RequestOptions().placeholder(R.drawable.default_head).error(R.drawable.default_head).dontAnimate().centerCrop())
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
