package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
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
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.iv_identity_code)
    ImageView ivIdentityCode;
    @BindView(R.id.et_message_code)
    EditText etMessageCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_goto_login)
    TextView tvGotoLogin;

    private String mPhoneNumber;
    private String mPassword;
    private String mConfirmPassword;
    private String mImageCode;
    private String mMessageCode;

    private SmsCountDownTimer mSmsCountDownTimer;
    private boolean mIsAgreementChecked;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_register);
        ButterKnife.bind(this);

        setTitleAndBackPressListener("注册蜂店");
        mSmsCountDownTimer = new SmsCountDownTimer(60000, 1000);
        ivIdentityCode.setBackground(new BitmapDrawable(CodeUtils.getInstance().createBitmap()));

        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsAgreementChecked = isChecked;
            }
        });
    }

    private boolean verify() {
        mPhoneNumber = etPhoneNumber.getText().toString();
        mPassword = etPassword.getText().toString();
        mImageCode = etIdentifyCode.getText().toString();
        mConfirmPassword = etConfirmPassword.getText().toString();
        mMessageCode = etMessageCode.getText().toString();
        if (TextUtils.isEmpty(mPhoneNumber)) {
            ToastUtils.showToast("请输入手机号");
            return false;
        } else if (mPhoneNumber.length() != 11) {
            ToastUtils.showToast("请输入正确手机号");
            return false;
        } else if (TextUtils.isEmpty(mImageCode)) {
            ToastUtils.showToast("请输入图片验证码");
            return false;
        } else if (TextUtils.isEmpty(mMessageCode)) {
            ToastUtils.showToast("请输入短信验证码");
            return false;
        } else if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.showToast("请输入密码");
            return false;
        } else if (TextUtils.isEmpty(mConfirmPassword)) {
            ToastUtils.showToast("请输入确认密码");
            return false;
        } else if (!TextUtils.equals(mImageCode.toLowerCase(),CodeUtils.getInstance().getImageCode().toLowerCase())) {
            ToastUtils.showToast("请输入正确图片验证码");
            return false;
        } else if (!mIsAgreementChecked) {
            ToastUtils.showToast("请阅读协议并同意");
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_identity_code, R.id.tv_get_code, R.id.tv_register, R.id.tv_forget_password, R.id.tv_goto_login,R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_identity_code:
                ivIdentityCode.setBackground(new BitmapDrawable(CodeUtils.getInstance().createBitmap()));
                break;
            case R.id.tv_get_code:
                mPhoneNumber = etPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                } else if (mPhoneNumber.length() != 11) {
                    ToastUtils.showToast("请输入正确手机号");
                    return;
                }
                getMessageCode();
                break;
            case R.id.tv_register:
                if (verify()) {
                    register();
                }
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.tv_goto_login:
                this.finish();
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(this,AgreementActivity.class));
                break;
        }
    }

    private void getMessageCode() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("phone", mPhoneNumber);
        params1.put("type", "1"); //1注册 2找回密码
        HttpLoader.getInstance().getMessageCode(params1, mCompositeSubscription, new SubscriberCallBack() {

            @Override
            protected void onSuccess() {
                super.onSuccess();
                mSmsCountDownTimer.start();
                ToastUtils.showToast("已发送验证码，请注意查收。");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

        });
    }

    /**
     * 验证码倒计时
     */
    class SmsCountDownTimer extends CountDownTimer {

        public boolean isRuning; // 是否正在倒数计时

        public SmsCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long second = millisUntilFinished / 1000 + 1;
            tvGetCode.setText(second + "s");
            isRuning = true;
            tvGetCode.setClickable(false);
        }

        @Override
        public void onFinish() {
            isRuning = false;
            tvGetCode.setText("获取验证码");
            tvGetCode.setClickable(true);
            this.cancel();
        }
    }

    private void register() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("phone", mPhoneNumber);
        params1.put("password", mPassword);
        params1.put("pic_token", "123");
        params1.put("pic_code", "123");
        params1.put("sms_code", mMessageCode);
        HttpLoader.getInstance().register(params1, mCompositeSubscription, new SubscriberCallBack() {

            @Override
            protected void onSuccess() {
                super.onSuccess();
                ToastUtils.showToast("成功注册，请登录。");
                RegisterActivity.this.finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                RegisterActivity.this.finish();
            }

        });
    }
}
