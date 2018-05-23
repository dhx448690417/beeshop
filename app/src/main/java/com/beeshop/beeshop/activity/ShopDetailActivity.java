package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.adapter.OnRecycleItemClickListener;
import com.beeshop.beeshop.adapter.ShopDetailProductAdapter;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopDetailEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Author : cooper
 * Time :  2018/5/8 下午2:08
 * Description : 店铺详情页
 */
public class ShopDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    BGABanner banner;
    @BindView(R.id.tv_shop_introduce)
    TextView tvShopIntroduce;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    public static final String PARAM_SHOP_ID = "id";
    public final int COLLECT_SHOP = 2; // 关注店铺
    public final int NOT_COLLECT_SHOP = 1; // 取消关注店铺

    private List<ShopDetailEntity.ProductBean> mShopList = new ArrayList<>();
    private ShopDetailProductAdapter mShopDetailProductAdapter;
    private int mShopId;
    private boolean mIsCollected; //是否关注收藏


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("蜂店");
        setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""))) {
                    startActivity(new Intent(ShopDetailActivity.this,LoginActivity.class));
                    return;
                }
                if (mIsCollected) {
                    collect(NOT_COLLECT_SHOP);
                } else {
                    collect(COLLECT_SHOP);
                }
            }
        });
        initBanner();
        mShopId = getIntent().getIntExtra(PARAM_SHOP_ID,0);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProducts.setLayoutManager(linearLayoutManager);
        mShopDetailProductAdapter = new ShopDetailProductAdapter(this, mShopList);
        rvProducts.setAdapter(mShopDetailProductAdapter);
        mShopDetailProductAdapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ShopDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PARAM_PRODUCT_ID, mShopList.get(position).getId());
                startActivity(intent);
            }
        });

        // 解决 当ScrollView中包含ListView时，页面不显示顶部bug
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
        getShopDetail();
    }

    private void initBanner() {
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
        List<String> imageList = new ArrayList<>();
        imageList.add("http://img4.imgtn.bdimg.com/it/u=3730565220,1475936246&fm=27&gp=0.jpg");
        imageList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=742456348,2972158456&fm=27&gp=0.jpg");
        imageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1757891169,1150399296&fm=27&gp=0.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3660015256,3208948633&fm=27&gp=0.jpg");

        banner.setData(imageList, null);
    }

    private void getShopDetail() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", "");
        params.put("id", mShopId);
        HttpLoader.getInstance().getShopDetail(params, mCompositeSubscription, new SubscriberCallBack<ShopDetailEntity>(this, this) {

            @Override
            protected void onSuccess(ShopDetailEntity response) {
                super.onSuccess(response);
                tvShopIntroduce.setText(response.getInfo().getBusiness());
                setTitle(response.getInfo().getTitle());
                ShopDetailEntity.ProductBean productBean = new ShopDetailEntity.ProductBean();
                productBean.setId(1);
                productBean.setDetails("水电费水电费及垃圾哦较为 高度发给");
                productBean.setTitle("高端产品");
                response.getProduct().add(productBean);
                mShopList.addAll(response.getProduct());
                mShopDetailProductAdapter.notifyDataSetChanged();
                if (response.getCollection() == 2) {
                    setRightText("取消关注");
                    mIsCollected = true;
                } else {
                    setRightText("添加关注");
                    mIsCollected = false;
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 是否关注or取消关注店铺
     * @param type
     */
    private void collect(int type) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        params.put("shop_id", mShopId);
        params.put("type", type);
        HttpLoader.getInstance().collect(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                if (mIsCollected) {
                    setRightText("添加关注");
                    mIsCollected = false;
                } else {
                    setRightText("取消关注");
                    mIsCollected = true;
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
