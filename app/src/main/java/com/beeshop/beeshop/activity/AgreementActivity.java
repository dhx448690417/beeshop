package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.beeshop.beeshop.R;

/**
 * Author : cooper
 * Time :  2018/7/17 上午11:14
 * Description : 协议
 */
public class AgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_agreement);
        setTitleAndBackPressListener("协议");
    }
}
