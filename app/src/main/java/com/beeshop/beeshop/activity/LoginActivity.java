package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.net.Test;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.CodeUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：cooper
 * Time：  2018/5/19 上午11:53
 * Description：
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    private String mPhoneNumber;
    private String mPassword;
    private String mImageCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("登录蜂店");
        initView();
    }


    private void initView() {
        ivIdentityCode.setBackground(new BitmapDrawable(CodeUtils.getInstance().createBitmap()));
    }

    private boolean verify() {
        mPhoneNumber = etPhoneNumber.getText().toString();
        mPassword = etPassword.getText().toString();
        mImageCode = etIdentifyCode.getText().toString();
        if (TextUtils.isEmpty(mPhoneNumber)) {
            ToastUtils.showToast("请输入手机号");
            return false;
        } else if (mPhoneNumber.length() != 11) {
            ToastUtils.showToast("请输入正确手机号");
            return false;
        } else if (TextUtils.isEmpty(mImageCode)) {
            ToastUtils.showToast("请输入图片验证码");
            return false;
        } else if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.showToast("请输入密码");
            return false;
        } else if (!TextUtils.equals(mImageCode.toLowerCase(),CodeUtils.getInstance().getImageCode().toLowerCase())) {
            ToastUtils.showToast("请输入正确图片验证码");
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_identity_code, R.id.tv_login, R.id.tv_forget_password, R.id.tv_goto_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_identity_code:
                ivIdentityCode.setBackground(new BitmapDrawable(CodeUtils.getInstance().createBitmap()));
                break;
            case R.id.tv_login:
                if (verify()) {
                    login();
                }
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.tv_goto_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }


    private void login() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("phone", mPhoneNumber);
        params1.put("password", mPassword);
        params1.put("pic_token", "123");
        params1.put("pic_code", "123");
        HttpLoader.getInstance().login(params1, mCompositeSubscription, new SubscriberCallBack<UserEntity>(){

            @Override
            protected void onSuccess(UserEntity response) {
                super.onSuccess(response);
                SharedPreferenceUtil.putUserPreferences(SharedPreferenceUtil.KEY_TOKEN,response.getToken());
                LoginActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
