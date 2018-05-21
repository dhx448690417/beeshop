package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：cooper
 * Time：  2018/5/17 下午10:00
 * Description：设置页面
 */
public class MineSettingActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sign_name)
    EditText etSignName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_identify_code)
    EditText etIdentifyCode;
    @BindView(R.id.iv_identity_code)
    ImageView ivIdentityCode;
    @BindView(R.id.iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_mine_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_identity_code, R.id.iv_add_image, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_identity_code:
                break;
            case R.id.iv_add_image:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
