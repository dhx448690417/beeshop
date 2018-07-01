package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
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
 * Description : 编辑产品
 */
public class ProductEditActivity extends BaseActivity {

    @BindView(R.id.et_product_name)
    EditText etProductName;
    @BindView(R.id.et_product_pirce)
    EditText etProductPirce;
    @BindView(R.id.et_product_unit)
    EditText etProductUnit;
    @BindView(R.id.et_product_total)
    EditText etProductTotal;
    @BindView(R.id.et_product_describe)
    EditText etProductDescribe;
    @BindView(R.id.tv_introduce_number)
    TextView tvIntroduceNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    public static final String PARAM_PRODUCT_INFO = "param_product_info";
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.tv_manager_pic)
    TextView tvManagerPic;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private String mProductTitle;
    private String mProductPrice;
    private String mProductUnit;
    private String mProductTotal;
    private String mProductDescribe;

    private ProductListEntity.ListBean mListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_edit_product);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("编辑产品");
        mListBean = (ProductListEntity.ListBean) getIntent().getSerializableExtra(PARAM_PRODUCT_INFO);
        initView();
    }

    private void initView() {
        etProductDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvIntroduceNumber.setText(s.length() + "/120");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (mListBean != null) {
            etProductName.setText(mListBean.getTitle());
            etProductPirce.setText(mListBean.getPrice());
            etProductUnit.setText(mListBean.getUnit());
//            etProductTotal.setText(mListBean.getTitle());
//            etProductDescribe.setText(mListBean.get);
            llBottom.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
        } else {
            llBottom.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.tv_submit,R.id.tv_update, R.id.tv_manager_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (verify()) {
                    addProduct();
                }
                break;
            case R.id.tv_update:
                if (verify()) {
                    updateProduct();
                }
                break;
            case R.id.tv_manager_pic:
                Intent intent = new Intent(ProductEditActivity.this, ProductPictureUploadActivity.class);
                intent.putExtra(ProductPictureUploadActivity.PARAM_PRODUCT_ID, mListBean.getId());
                startActivity(intent);
                break;
        }
    }

    private boolean verify() {
        mProductTitle = etProductName.getText().toString();
        mProductPrice = etProductPirce.getText().toString();
        mProductUnit = etProductUnit.getText().toString();
        mProductTotal = etProductTotal.getText().toString();
        mProductDescribe = etProductDescribe.getText().toString();
        if (TextUtils.isEmpty(mProductTitle)) {
            ToastUtils.showToast("请填写产品名称");
            return false;
        }

        if (TextUtils.isEmpty(mProductDescribe)) {
            ToastUtils.showToast("请填写产品描述");
            return false;
        }
        return true;
    }

    /**
     * 添加产品
     */
    private void addProduct() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("title", mProductTitle);
        params.put("price", mProductPrice);
        params.put("unit", mProductUnit);
        params.put("details", mProductDescribe);
//        params.put("total", mProductTotal);
        HttpLoader.getInstance().addProduct(params, mCompositeSubscription, new SubscriberCallBack<ProductInfo>(this, this) {

            @Override
            protected void onSuccess(ProductInfo response) {
                super.onSuccess(response);
                Intent intent = new Intent(ProductEditActivity.this, ProductPictureUploadActivity.class);
                intent.putExtra(ProductPictureUploadActivity.PARAM_PRODUCT_ID, response.getProduct_id());
                startActivity(intent);
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
    private void updateProduct() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("product_id", mListBean.getId());
        params.put("title", mProductTitle);
        params.put("price", mProductPrice);
        params.put("unit", mProductUnit);
        params.put("details", mProductDescribe);
//        params.put("total", mProductTotal);
        HttpLoader.getInstance().updateProduct(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

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

}
