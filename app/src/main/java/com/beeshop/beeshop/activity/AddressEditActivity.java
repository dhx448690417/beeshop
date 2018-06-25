package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.AddressEntity;
import com.beeshop.beeshop.model.ProductInfo;
import com.beeshop.beeshop.model.ProductListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/6/11 上午11:29
 * Description : 编辑收货地址
 */
public class AddressEditActivity extends BaseActivity {


    public static final String PARAM_ADDRESS_INFO = "param_address_info";

    @BindView(R.id.et_address_tag)
    EditText etAddressTag;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private String mAddressTag;
    private String mAddress;
    private String mAddressPeople;
    private String mAddressPeoplePhone;

    private AddressEntity.ListBean mListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_edit_address);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("编辑收货地址");
        mListBean = (AddressEntity.ListBean) getIntent().getSerializableExtra(PARAM_ADDRESS_INFO);
        initView();
    }

    private void initView() {

        if (mListBean != null) {
            etAddressTag.setText(mListBean.getTag());
            etAddress.setText(mListBean.getDetailed());
            etPeople.setText(mListBean.getAddressee());
            etPhone.setText(mListBean.getPhone());
            tvSubmit.setText("更新");
        } else {
            tvSubmit.setText("提交");
        }

    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (verify()) {
                    if (mListBean != null) {
                        updateAddress();
                    } else {
                        addAddress();
                    }
                }
                break;
        }
    }

    private boolean verify() {
        mAddressTag = etAddressTag.getText().toString();
        mAddress = etAddress.getText().toString();
        mAddressPeople = etPeople.getText().toString();
        mAddressPeoplePhone = etPhone.getText().toString();
        if (TextUtils.isEmpty(mAddressTag)) {
            ToastUtils.showToast("请填写地址标签");
            return false;
        }

        if (TextUtils.isEmpty(mAddress)) {
            ToastUtils.showToast("请填写地址");
            return false;
        }

        if (TextUtils.isEmpty(mAddressPeople)) {
            ToastUtils.showToast("请填写收货人姓名");
            return false;
        }

        if (TextUtils.isEmpty(mAddressPeoplePhone)) {
            ToastUtils.showToast("请填写收货人电话号码");
            return false;
        }
        return true;
    }

    /**
     * 添加产品
     */
    private void addAddress() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("tag", mAddressTag);
        params.put("detailed", mAddress);
        params.put("phone", mAddressPeoplePhone);
        params.put("addressee", mAddressPeople);
        HttpLoader.getInstance().addAddress(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess() {
                super.onSuccess();
                finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 更新产品
     */
    private void updateAddress() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id", mListBean.getId());
        params.put("tag", mAddressTag);
        params.put("detailed", mAddress);
        params.put("phone", mAddressPeoplePhone);
        params.put("addressee", mAddressPeople);
        HttpLoader.getInstance().updateAddress(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess() {
                finish();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

}
