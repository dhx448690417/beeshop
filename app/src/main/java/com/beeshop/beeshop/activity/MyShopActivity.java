package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/5/26 下午3:16
 * Description :
 */
public class MyShopActivity extends BaseActivity {

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
        setContentView(R.layout.aty_my_shop);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("我的店铺");
    }


    @OnClick({R.id.rl_shop_info, R.id.rl_shop_photo, R.id.rl_shop_product, R.id.rl_vip_type, R.id.rl_shop_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_shop_info:
                startActivity(new Intent(this,ShopManagerActivity.class));
                break;
            case R.id.rl_shop_photo:
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
        }
    }
}
