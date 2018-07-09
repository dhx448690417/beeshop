package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.ProductDetailEntity;
import com.beeshop.beeshop.model.ShopDetailEntity;
import com.beeshop.beeshop.net.ApiManager;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.RSAUtil;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.RetrofitManager;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.net.Test;
import com.beeshop.beeshop.utils.GsonUtil;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author : cooper
 * Time :  2018/5/8 下午7:33
 * Description :
 */
public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_repertory)
    TextView tvRepertory;
    @BindView(R.id.tv_product_introduce)
    TextView tvProductIntroduce;
    @BindView(R.id.tv_contect_shop)
    TextView tvContectShop;
    @BindView(R.id.tv_buy)
    TextView tvBuy;

    public static final String PARAM_PRODUCT_ID ="product_id";

    private int mProductId;
    private ProductDetailEntity mProductDetailEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_product_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("产品详情");
        mProductId = getIntent().getIntExtra(PARAM_PRODUCT_ID, 0);
        initView();
    }

    private void initView() {

        banner.setAutoPlayAble(true);
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                        .into(itemView);
            }
        });

        getProductInfo();
    }

    private void setBanner(ProductDetailEntity productDetailEntity) {
        List<String> titleList = new ArrayList<>();
        List<String> picList = new ArrayList<>();

        if (productDetailEntity != null && productDetailEntity.getPic() != null && productDetailEntity.getPic().size() > 0) {
            for (int i = 0; i < productDetailEntity.getPic().size(); i++) {
                ProductDetailEntity.PicBean picBean = productDetailEntity.getPic().get(i);
                titleList.add(picBean.getTitle());
                picList.add(picBean.getPath());
            }
        } else {
            titleList.add("暂无图片");
            picList.add("");
        }

        if (picList.size() > 1) {
            banner.setAutoPlayAble(true);
        } else {
            banner.setAutoPlayAble(false);
        }
        banner.setData(picList,titleList);
    }

    @OnClick({R.id.tv_contect_shop, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_contect_shop:
                break;
            case R.id.tv_buy:
                if (TextUtils.isEmpty(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""))) {
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(this,OrderActivity.class);
                intent.putExtra(OrderActivity.PARAM_PRODUCT_KEY, mProductDetailEntity);
                startActivity(intent);
                break;
        }
    }

    private void getProductInfo() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("id", mProductId);

        HttpLoader.getInstance().getProductInfo(params1, mCompositeSubscription, new SubscriberCallBack<ProductDetailEntity>(this,this) {

            @Override
            protected void onSuccess(ProductDetailEntity response) {
                super.onSuccess(response);
                setBanner(response);
                mProductDetailEntity = response;
                tvPrice.setText(response.getPrice());
                tvProductIntroduce.setText(response.getDetails());
                tvRepertory.setText(response.getSupply()+"");
                tvUnit.setText(response.getUnit());
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                LogUtil.e(errorBean.getMsg());
            }

        });
    }

}
