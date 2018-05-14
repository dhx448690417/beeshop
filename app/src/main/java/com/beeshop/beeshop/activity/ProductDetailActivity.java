package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_product_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("蜂店详情");
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
        List<String> imageList = new ArrayList<>();
        imageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=394591684,703198697&fm=27&gp=0.jpg");
        imageList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=742456348,2972158456&fm=27&gp=0.jpg");
        imageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1757891169,1150399296&fm=27&gp=0.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3660015256,3208948633&fm=27&gp=0.jpg");

        banner.setData(imageList, null);
    }

    @OnClick({R.id.tv_contect_shop, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_contect_shop:
//                String content = "sZGWD4CwpRVlnYQ/egoYlPBewobjNcEFu6pQywqpNq4UcANjBb3UjXxd7PbDTylBgLb4AXcRdgHC4+vxYVHZEvqQD209KunXYw0lzH4MrJyjLm/5diK1PZbMNUy+YXyWsEL077wk+km0EgVYlUIuFNUJjkVnzzQMtLg5FF9cikdNZaVZtLjP+2f0WK2wWgG3ySJIyV2SuPFZXghfFJhVhyEbqrCoThbyczqkeIaCMghLXSGp0z6WaiNnNX8Jr8k1zNNHtB93FSfYPHPhl7n1qEPtEdjoVQqj6RpTXfqTcb+/PGxQYWzWpjFD+VlXmnJin+fzfZfxluZXbOv+BK4GjA==";
//                String content = "{\"data\":\"{\\\"xx2\\\":\\\"222\\\",\\\"xx1\\\":\\\"111\\\",\\\"xx3\\\":\\\"333\\\"}\",\"code\":200,\"msg\":\"SUCCESS\",\"time\":1525919228}";



                //获取公钥
//                PublicKey publicKey = RSAUtil.keyStrToPublicKey(RSAUtil.PUBLIC_KEY_STR);
//                PrivateKey privateKey = RSAUtil.keyStrToPrivate(RSAUtil.PRIVATE_KEY_STR);
//
//                String res = null;
//                try {
//                    res = RSAUtil.encryptByPublicKey(content.getBytes(), privateKey);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


//                String respone = null;
//                try {
//                    respone = RSAUtil.decryptByPublicKey(Base64.decode(content,Base64.DEFAULT), publicKey);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                test1();
                ToastUtils.showToast("联系商家 === ");
//                LogUtil.e("ah   respone ==  "+respone);
                break;
            case R.id.tv_buy:
                ToastUtils.showToast("立即购买");
                break;
        }
    }

    private void test1() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("xx1", "111");
        params1.put("xx2", "222");
        params1.put("xx3", "333");

        HttpLoader.getInstance().getTest(params1, mCompositeSubscription, new SubscriberCallBack<Test>() {
            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                LogUtil.e(errorBean.getMsg());
            }

            @Override
            protected void onSuccess(Test response) {
                super.onSuccess(response);
                ToastUtils.showToast(response.toString());
                LogUtil.e(response.toString());
            }
        });
    }

//    private void test() {
//
//        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("xx1", "111");
//        params1.put("xx2", "222");
//        params1.put("xx3", "333");
//
//        Gson gson = new Gson();
//
//        String requestStr = gson.toJson(params1);
//
//        //获取公钥
//        PublicKey publicKey = RSAUtil.keyStrToPublicKey(RSAUtil.PUBLIC_KEY_STR);
//
//        requestStr = RSAUtil.encryptDataByPublicKey(requestStr.getBytes(), publicKey);
//        try {
//            requestStr = RSAUtil.encryptByPublicKey(requestStr.getBytes(), publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ApiManager apiManager = RetrofitManager.getInstance().create(ApiManager.class);
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("version", "1.0.0");
//        params.put("rsa", "201805");
//        params.put("data", requestStr);
//        Call<String> call = apiManager.postLogin(params);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                LogUtil.e("ah  onResponse == "+response.body());
//                String content = response.body();
//
//                // 获取公钥
//                PublicKey publicKey = RSAUtil.keyStrToPublicKey(RSAUtil.PUBLIC_KEY_STR);
//                String respone = null;
//                try {
//                    respone = RSAUtil.decryptByPublicKey(Base64.decode(content,Base64.DEFAULT));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ToastUtils.showToast("respone === "+respone);
//                LogUtil.e("ah   respone ==  "+respone);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                t.printStackTrace();
//                LogUtil.e("ah   ==  onFailure");
//            }
//        });
//    }
}
