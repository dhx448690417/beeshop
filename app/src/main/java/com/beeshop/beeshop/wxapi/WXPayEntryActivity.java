package com.beeshop.beeshop.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.BaseActivity;
import com.beeshop.beeshop.activity.OrderConfirmBuyActivity;
import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.utils.AppManager;
import com.beeshop.beeshop.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @BindView(R.id.pay_success)
    TextView paySuccess;
    private IWXAPI api;
    private boolean isPaySuccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_pay_success);
        ButterKnife.bind(this);
        setTitle("支付结果");
        setBackPressListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaySuccess) {
                    AppManager.getAppManager().finishActivity(OrderConfirmBuyActivity.class);
                }
                WXPayEntryActivity.this.finish();
            }
        });

        api = WXAPIFactory.createWXAPI(this, AppConfig.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isPaySuccess) {
            AppManager.getAppManager().finishActivity(OrderConfirmBuyActivity.class);
        }
        WXPayEntryActivity.this.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e("ah  WXPayEntryActivity   onResp == " + resp.errCode + "  errorStr  ==  " + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode == 0) {
            paySuccess.setText("支付成功");
            isPaySuccess = true;
        } else if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode == -1){
            paySuccess.setText("支付失败");
            isPaySuccess = false;
        } else if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode == -2){
            paySuccess.setText("用户取消支付");
            isPaySuccess = false;
        }
    }
}