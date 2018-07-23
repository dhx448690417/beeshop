package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.model.OrderCreateEntity;
import com.beeshop.beeshop.model.WeiXinPayEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.GsonUtil;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.rxbus2.Subscribe;
import com.luck.picture.lib.tools.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author : cooper
 * Time :  2018/6/25 下午4:47
 * Description :
 */
public class OrderConfirmBuyActivity extends BaseActivity {

    public static final String PARAM_ORDER_KEY = "param_order_key";

    //1微信 2支付宝 3vip支付
    private final int PAY_STYLE_WEIXIN = 1;
    private final int PAY_STYLE_ZHIFUBAO = 2;
    private final int PAY_STYLE_VIP = 3;

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
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_confirm_buy);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("订单支付");

        mOrderCreateEntity = (OrderCreateEntity) getIntent().getSerializableExtra(PARAM_ORDER_KEY);

        initWeiXinPay();
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

    private void initWeiXinPay() {
        mWxApi = WXAPIFactory.createWXAPI(this, AppConfig.WEIXIN_APP_ID,true);
        mWxApi.registerApp(AppConfig.WEIXIN_APP_ID);
    }


    @OnClick({R.id.rl_weixin_pay, R.id.rl_zhifubao_pay, R.id.rl_vip_pay, R.id.tv_confirm_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_weixin_pay:
                mPayStyle = PAY_STYLE_WEIXIN;
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_zhifubao_pay:
                mPayStyle = PAY_STYLE_ZHIFUBAO;
                ivWeixinCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                ivZhifubaoCheck.setBackgroundResource(R.drawable.icon_chat_album_selected);
                ivVipCheck.setBackgroundResource(R.drawable.icon_chat_album_unselected);
                break;
            case R.id.rl_vip_pay:
                mPayStyle = PAY_STYLE_VIP;
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
                    switch (mPayStyle) {
                        case PAY_STYLE_WEIXIN:
                            JSONObject payObject = response.getJSONObject("pay");
                            WeiXinPayEntity weiXinPayEntity = GsonUtil.gsonToBean(payObject.toString(), WeiXinPayEntity.class);
                            useWxPay(weiXinPayEntity);
                            break;
                        case PAY_STYLE_ZHIFUBAO:
                            String orderInfo = response.getString("pay");
                            if (!TextUtils.isEmpty(orderInfo)) {
                                useZFB(orderInfo);
                            }
                            break;
                        case PAY_STYLE_VIP:
                            break;
                    }

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

    /**
     * 微信支付
     * @param weiXinPayEntity
     */
    private void useWxPay(WeiXinPayEntity weiXinPayEntity) {

        PayReq request = new PayReq();
        request.appId = weiXinPayEntity.getAppid();
        request.partnerId = weiXinPayEntity.getPartnerid();
        request.prepayId= weiXinPayEntity.getPrepayid();
        request.packageValue = weiXinPayEntity.getPackageX();
        request.nonceStr= weiXinPayEntity.getNoncestr();
        request.timeStamp= weiXinPayEntity.getTimestamp();
        request.sign= weiXinPayEntity.getSign();

        mWxApi.sendReq(request);
    }


    /**
     * 支付宝支付
     * @param orderInfo
     */
    private void useZFB(String orderInfo) {
        fixedThreadPool.execute(new ZhiFuBaoPayRunnable(orderInfo));
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, String> result = (Map<String, String>) msg.obj;
            LogUtil.e("ah  mHandler result ====  "+result.toString());

            String resultStatus = result.get("resultStatus");

            if (TextUtils.equals(resultStatus, "9000")) {
                startActivity(new Intent(OrderConfirmBuyActivity.this,OrderPaySuccessActivity.class));
                OrderConfirmBuyActivity.this.finish();
            } else {
                String memo = TextUtils.isEmpty(result.get("memo")) ? "支付失败" : result.get("memo");
                ToastUtils.showToast(memo);
            }
        }
    };

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    class ZhiFuBaoPayRunnable implements Runnable{

        private String orderInfo;

        public ZhiFuBaoPayRunnable(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public void run() {
            LogUtil.e("ah     orderInfo == "+orderInfo);
            PayTask alipay = new PayTask(OrderConfirmBuyActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            LogUtil.e("ah  run  result ====  "+result.toString());

            Message msg = new Message();
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    }

    private void useZhiFuBao(final String orderInfo) {

        Observable.create(new Observable.OnSubscribe<Map<String, String>>(){
            @Override
            public void call(Subscriber<? super Map<String, String>> subscriber) {
                PayTask alipay = new PayTask(OrderConfirmBuyActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Map<String, String> stringStringMap) {
                        LogUtil.e(stringStringMap.toString());
                        LogUtil.e(stringStringMap.toString());
                    }
                });
    }
}
