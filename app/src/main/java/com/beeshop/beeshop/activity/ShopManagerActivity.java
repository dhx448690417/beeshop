package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：cooper
 * Time：  2018/5/20 上午11:58
 * Description：
 */
public class ShopManagerActivity extends BaseActivity {

    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.rg_shop_type)
    RadioGroup rgShopType;
    @BindView(R.id.sp_shop_type)
    Spinner spShopType;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_people_name)
    EditText etPeopleName;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_business_license_code)
    EditText etBusinessLicenseCode;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_manager);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("商铺管理");
    }


}
