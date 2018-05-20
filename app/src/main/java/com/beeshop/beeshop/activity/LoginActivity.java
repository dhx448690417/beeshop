package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：cooper
 * Time：  2018/5/19 上午11:53
 * Description：
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.iv_identity_code)
    ImageView ivIdentityCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_goto_register)
    TextView tvGotoRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        ButterKnife.bind(this);

        setTitleAndBackPressListener("登录蜂店");
    }
}
