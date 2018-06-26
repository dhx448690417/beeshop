package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.AddressEntity;
import com.beeshop.beeshop.model.OrderCreateEntity;
import com.beeshop.beeshop.model.ProductDetailEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.TypeRadioView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/6/17 下午12:40
 * Description : 下单页
 */
public class OrderActivity extends BaseActivity {

    public static final String PARAM_PRODUCT_KEY = "param_product_entity";

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.iv_product_count_subtract)
    ImageView ivProductCountSubtract;
    @BindView(R.id.tv_product_count)
    TextView tvProductCount;
    @BindView(R.id.iv_product_count_plus)
    ImageView ivProductCountPlus;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.trv_address)
    TypeRadioView trvAddress;

    private List<TypeRadioView.TypeItem> typeItemList = new ArrayList<>();
    private ProductDetailEntity mProductDetailEntity;
    private int mCount = 1;
    private String mAddressId = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_order);
        ButterKnife.bind(this);

        setTitleAndBackPressListener("产品下单");

        mProductDetailEntity = (ProductDetailEntity) getIntent().getSerializableExtra(PARAM_PRODUCT_KEY);

        initView();
    }

    private void initView() {
        Glide.with(this).load(mProductDetailEntity.getCover())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(ivProduct);
        tvProductName.setText(mProductDetailEntity.getTitle());
        tvProductPrice.setText("单价："+mProductDetailEntity.getPrice()+"元");
        tvProductDetail.setText(mProductDetailEntity.getDetails());
        tvProductCount.setText(mCount+"");

        trvAddress.addTypeItem(TypeRadioView.with()
                .addItem(new TypeRadioView.TypeItem("无","0",true))
                .create());

        trvAddress.setTypeRadioCheckedCallBack(new TypeRadioView.TypeRadioCheckedCallBack() {
            @Override
            public void itemChecked(int position, String key) {
                mAddressId = key;
            }
        });

        getMyAddress();
    }

    @OnClick({R.id.iv_product_count_subtract, R.id.iv_product_count_plus,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_product_count_subtract:
                if (mCount > 1) {
                    mCount--;
                    tvProductCount.setText(mCount+"");
                }
                break;
            case R.id.iv_product_count_plus:
                if (mCount < mProductDetailEntity.getSupply()) {
                    mCount--;
                    tvProductCount.setText(mCount + "");
                } else {
                    ToastUtils.showToast("已达到库存最大数量");
                }
                break;

            case R.id.tv_submit:
                createOrder();
                break;
        }
    }

    /**
     * 下单
     */
    private void createOrder() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("product_id", mProductDetailEntity.getId());
        params1.put("num", mCount);
        params1.put("address", mAddressId);
        HttpLoader.getInstance().createOrder(params1, mCompositeSubscription, new SubscriberCallBack<OrderCreateEntity>(this, this) {

            @Override
            protected void onSuccess(OrderCreateEntity response) {
                super.onSuccess(response);
                if (response != null) {
                    ToastUtils.showToast("下单成功");
                    response.setDetails(mProductDetailEntity.getDetails());
                    response.setUnit(mProductDetailEntity.getUnit());
                    Intent intent = new Intent(OrderActivity.this, OrderConfirmBuyActivity.class);
                    intent.putExtra(OrderConfirmBuyActivity.PARAM_ORDER_KEY, response);
                    startActivity(intent);
                    finish();
                }
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }

    /**
     * 获取我的地址列表
     */
    private void getMyAddress() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getMyAddress(params1, mCompositeSubscription, new SubscriberCallBack<AddressEntity>(this, this) {

            @Override
            protected void onSuccess(AddressEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    TypeRadioView.TypeItem wuTypeItem = new TypeRadioView.TypeItem("无", "0", true);
                    typeItemList.add(wuTypeItem);
                    for (int i = 0; i < response.getList().size(); i++) {
                        AddressEntity.ListBean listBean = response.getList().get(i);
                        TypeRadioView.TypeItem typeItem = new TypeRadioView.TypeItem(listBean.getTag(), listBean.getId() + "", false);
                        typeItemList.add(typeItem);
                    }
                    trvAddress.addTypeItem(typeItemList);
                }
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }
}
