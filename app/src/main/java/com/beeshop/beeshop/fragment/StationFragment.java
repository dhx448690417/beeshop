package com.beeshop.beeshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author : cooper
 * Time :  2018/5/4 下午3:27
 * Description : 电台
 */

public class StationFragment extends BaseFragment {


    @BindView(R.id.rl_shop_sort)
    RelativeLayout rlShopSort;
    @BindView(R.id.rl_nearby_broadcast)
    RelativeLayout rlNearbyBroadcast;
    @BindView(R.id.rl_want_boradcast)
    RelativeLayout rlWantBoradcast;

    @Override
    protected View getRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_station, container, false);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.rl_shop_sort, R.id.rl_nearby_broadcast, R.id.rl_want_boradcast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_shop_sort:
                ToastUtils.showToast("店铺排行");
                break;
            case R.id.rl_nearby_broadcast:
                ToastUtils.showToast("附近广播");
                break;
            case R.id.rl_want_boradcast:
                ToastUtils.showToast("我要广播");
                break;
        }
    }
}
