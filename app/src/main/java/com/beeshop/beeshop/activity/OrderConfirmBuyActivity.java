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
 * Time :  2018/6/25 下午4:47
 * Description :
 */
public class OrderConfirmBuyActivity extends BaseActivity {

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_buy_count)
    TextView tvBuyCount;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
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
    @BindView(R.id.tv_confirm_buy)
    TextView tvConfirmBuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_confirm_buy);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_weixin_pay, R.id.rl_zhifubao_pay, R.id.rl_vip_pay, R.id.tv_confirm_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_weixin_pay:
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_zhifubao_pay:
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_vip_pay:
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                break;
            case R.id.tv_confirm_buy:

                break;
        }
    }
}
