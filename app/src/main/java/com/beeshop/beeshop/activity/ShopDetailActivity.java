package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ProductAdapter;
import com.beeshop.beeshop.views.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
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
    @BindView(R.id.lvsv_products)
    ListViewForScrollView lvsvProducts;

    private List<String> mShopList = new ArrayList<>();
    private ProductAdapter mProductAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("蜂店");
        initBanner();

        initView();
    }

    private void initView() {
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mShopList.add("sdfsdf");
        mProductAdapter = new ProductAdapter(this, mShopList);
        lvsvProducts.setAdapter(mProductAdapter);
        lvsvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // 解决 当ScrollView中包含ListView时，页面不显示顶部bug
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
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
        imageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=394591684,703198697&fm=27&gp=0.jpg");
        imageList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=742456348,2972158456&fm=27&gp=0.jpg");
        imageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1757891169,1150399296&fm=27&gp=0.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3660015256,3208948633&fm=27&gp=0.jpg");

        banner.setData(imageList, null);
    }
}
