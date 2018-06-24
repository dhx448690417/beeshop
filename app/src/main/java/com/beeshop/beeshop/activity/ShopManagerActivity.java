package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.ShopMineInfoEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/5/26 下午3:16
 * Description :
 */
public class ShopManagerActivity extends BaseActivity {

    @BindView(R.id.iv_shop_pic)
    ImageView ivShopPic;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_type)
    TextView tvShopType;
    @BindView(R.id.tv_shop_status)
    TextView tvShopStatus;
    @BindView(R.id.tv_shop_create_time)
    TextView tvShopCreateTime;
    @BindView(R.id.rl_shop_info)
    RelativeLayout rlShopInfo;
    @BindView(R.id.rl_shop_photo)
    RelativeLayout rlShopPhoto;
    @BindView(R.id.rl_shop_product)
    RelativeLayout rlShopProduct;
    @BindView(R.id.rl_vip_type)
    RelativeLayout rlVipType;
    @BindView(R.id.rl_shop_vip)
    RelativeLayout rlShopVip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_manager);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("店铺管理");
    }


    @OnClick({R.id.rl_shop_info, R.id.rl_shop_photo,R.id.rl_shop_order_list, R.id.rl_shop_product, R.id.rl_vip_type, R.id.rl_shop_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_shop_info:
                startActivity(new Intent(this,ShopInfoEditActivity.class));
                break;
            case R.id.rl_shop_photo:
                startActivity(new Intent(this,ShopPictureUploadActivity.class));
                break;
            case R.id.rl_shop_product:
                startActivity(new Intent(this,ProductManagerActivity.class));
                break;
            case R.id.rl_vip_type:
                startActivity(new Intent(this,VipTypeActivity.class));
                break;
            case R.id.rl_shop_vip:
                startActivity(new Intent(this,VipMyMemberActivity.class));
                break;
            case R.id.rl_shop_order_list:
                startActivity(new Intent(this,ShopOrderListActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getShopInfo();
    }

    private void setShopInfo(ShopMineInfoEntity response) {
        Glide.with(this).load(response.getCover())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(ivShopPic);
        tvShopName.setText(response.getTitle());
        tvShopType.setText("门店类型："+(response.getCategory() == 1?"个人":"企业"));
        if (response.getStatus() == 1) {
            tvShopStatus.setText("门店状态：审核中");
        } else if (response.getStatus() == 2) {
            tvShopStatus.setText("门店状态：营业中");
        } else if (response.getStatus() == 3) {
            tvShopStatus.setText("门店状态：暂停营业");
        }
        if (response.getCreated_time() != 0) {
            Date d = new Date(response.getCreated_time());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tvShopCreateTime.setText(sdf.format(d));
        }
    }

    /**
     * 获取店铺信息
     */
    private void getShopInfo() {
        showProgress();
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getShopInfo(params1, mCompositeSubscription, new SubscriberCallBack<ShopMineInfoEntity>(this, this) {

            @Override
            protected void onSuccess(ShopMineInfoEntity response) {
                super.onSuccess(response);
                setShopInfo(response);
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }
}
