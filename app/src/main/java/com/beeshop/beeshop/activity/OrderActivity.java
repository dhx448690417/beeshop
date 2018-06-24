package com.beeshop.beeshop.activity;

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
 * Time :  2018/6/17 下午12:40
 * Description : 下单页
 */
public class OrderActivity extends BaseActivity {

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.iv_product_count_subtract)
    ImageView ivProductCountSubtract;
    @BindView(R.id.tv_product_count)
    TextView tvProductCount;
    @BindView(R.id.iv_product_count_plus)
    ImageView ivProductCountPlus;
    @BindView(R.id.iv_weixin_check)
    ImageView ivWeixinCheck;
    @BindView(R.id.rl_weixin_pay)
    RelativeLayout rlWeixinPay;
    @BindView(R.id.iv_zhifubao_check)
    ImageView ivZhifubaoCheck;
    @BindView(R.id.rl_zhifubao_pay)
    RelativeLayout rlZhifubaoPay;
    @BindView(R.id.iv_vip_check)
    ImageView ivVipCheck;
    @BindView(R.id.rl_vip_pay)
    RelativeLayout rlVipPay;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order);
        ButterKnife.bind(this);

        setTitleAndBackPressListener("产品下单");
    }

    @OnClick({R.id.iv_product_count_subtract, R.id.iv_product_count_plus, R.id.rl_weixin_pay, R.id.rl_zhifubao_pay, R.id.rl_vip_pay, R.id.rl_address_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_product_count_subtract:
                break;
            case R.id.iv_product_count_plus:
                break;
            case R.id.rl_weixin_pay:
                break;
            case R.id.rl_zhifubao_pay:
                break;
            case R.id.rl_vip_pay:
                break;
            case R.id.rl_address_home:
                break;
        }
    }
}
