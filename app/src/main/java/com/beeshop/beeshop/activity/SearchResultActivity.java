package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.model.SearchShopEntity;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 上午11:38
 * Description：搜索结果
 */
public class SearchResultActivity extends BaseListActivity<Shop.ListBean> {

    private HomeShopAdapter mSearchShops;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("店铺搜索结果");
    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mSearchShops = new HomeShopAdapter(this,mList);
        return mSearchShops;
    }

    @Override
    protected void initView() {
        super.initView();
        searchShops();
    }

    private void searchShops() {
        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("category", "");
        HttpLoader.getInstance().getSearchShops(params1, mCompositeSubscription, new SubscriberCallBack<SearchShopEntity>(this,this){

            @Override
            protected void onSuccess(SearchShopEntity response) {
                super.onSuccess(response);
                mList.clear();
                mList.addAll(response.getList());
                mSearchShops.notifyDataSetChanged();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
