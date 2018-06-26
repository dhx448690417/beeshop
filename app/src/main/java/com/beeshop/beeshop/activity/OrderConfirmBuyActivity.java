package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.OrderCreateEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/6/25 下午4:47
 * Description :
 */
public class OrderConfirmBuyActivity extends BaseActivity {

    public static final String PARAM_ORDER_KEY = "param_order_key";

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

    private OrderCreateEntity mOrderCreateEntity;
    private int mPayStyle = 1; // 支付方式
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_confirm_buy);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("订单支付");

        mOrderCreateEntity = (OrderCreateEntity) getIntent().getSerializableExtra(PARAM_ORDER_KEY);

        initView();
    }

    private void initView() {
        Glide.with(this).load(mOrderCreateEntity.getCover())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(ivProduct);
        tvProductName.setText(mOrderCreateEntity.getTitle());
        tvProductPrice.setText("单价："+mOrderCreateEntity.getPrice()+"元");
        tvProductDetail.setText(mOrderCreateEntity.getDetails());
        tvBuyCount.setText("购买数量："+mOrderCreateEntity.getNum()+mOrderCreateEntity.getUnit());
        tvOrderId.setText("订单编号："+mOrderCreateEntity.getOrder_no());
        tvOrderMoney.setText("订单金额："+mOrderCreateEntity.getReal_payment());

        if (TextUtils.equals(mOrderCreateEntity.getIsvip(), "1")) {
            rlVipPay.setVisibility(View.VISIBLE);
        } else {
            rlVipPay.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.rl_weixin_pay, R.id.rl_zhifubao_pay, R.id.rl_vip_pay, R.id.tv_confirm_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_weixin_pay:
                mPayStyle = 1;
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_zhifubao_pay:
                mPayStyle = 2;
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_vip_pay:
                mPayStyle = 3;
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                break;
            case R.id.tv_confirm_buy:
                confirmPay();
                break;
        }
    }

    /**
     * 支付
     */
    private void confirmPay() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("order_id", mOrderCreateEntity.getOrder_id());
        params1.put("payment", mPayStyle); // 支付方式 1微信 2支付宝 3vip支付
        HttpLoader.getInstance().confirmPay(params1, mCompositeSubscription, new SubscriberCallBack<JSONObject>(this, this) {

            @Override
            protected void onSuccess(JSONObject response) {
                super.onSuccess(response);
                if (response != null) {
                    ToastUtils.showToast("支付成功");
                    finish();
                }
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
