package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.BroadcastListEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Author : cooper
 * Time :  2018/5/26 下午6:28
 * Description : 广播详情
 */
public class BroadcastDetailActivity extends BaseActivity {

    public static final String PARAM_BROADCAST_ENTITY = "param_broadcast_entity";

    @BindView(R.id.tv_broadcast_title)
    TextView tvBroadcastTitle;
    @BindView(R.id.banner_broadcast)
    BGABanner bannerBroadcast;
    @BindView(R.id.tv_broadcast_content)
    TextView tvBroadcastContent;

    private BroadcastListEntity.ListBean mBroadcastEntity ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_broadcast_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("广播详情");

        mBroadcastEntity = (BroadcastListEntity.ListBean) getIntent().getSerializableExtra(PARAM_BROADCAST_ENTITY);
        initView();
    }

    private void initView() {
        tvBroadcastContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvBroadcastContent.setText(mBroadcastEntity.getDescribe());
        tvBroadcastTitle.setText(mBroadcastEntity.getTitle());
        initBanner();
    }

    private void initBanner() {
        bannerBroadcast.setAutoPlayAble(true);
        bannerBroadcast.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                        .into(itemView);
            }
        });
        List<String> imageList = new ArrayList<>();
        imageList.add(mBroadcastEntity.getImg());
        if (imageList.size() > 1) {
            bannerBroadcast.setAutoPlayAble(true);
        } else {
            bannerBroadcast.setAutoPlayAble(false);
        }
        bannerBroadcast.setData(imageList, null);
    }
}
