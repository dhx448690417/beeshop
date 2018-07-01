package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.beeshop.beeshop.R;

/**
 * Author : cooper
 * Time :  2018/6/28 下午5:54
 * Description :
 */
public class OrderPaySuccessActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order_pay_success);
        setTitleAndBackPressListener("支付结果");
    }
}
