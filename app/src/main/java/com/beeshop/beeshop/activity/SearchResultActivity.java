package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.HomeShopAdapter;
import com.beeshop.beeshop.adapter.OnRecycleItemClickListener;
import com.beeshop.beeshop.model.SearchShopEntity;
import com.beeshop.beeshop.model.Shop;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 上午11:38
 * Description：搜索结果
 */
public class SearchResultActivity extends BaseListActivity<Shop.ListBean> {

    public static final String PARAM_SEARCH_CATEGORY = "param_search_category";
    public static final String PARAM_SEARCH_TITLE = "param_search_title";

    private HomeShopAdapter mSearchShops;
    private String mCategory;
    private String mTitle;
    private int mCurrentPage = 1;
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
        mSearchShops.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchResultActivity.this, ShopDetailActivity.class);
                intent.putExtra(ShopDetailActivity.PARAM_SHOP_ID, mList.get(position).getId());
                startActivity(intent);
            }
        });
        return mSearchShops;
    }

    @Override
    protected void initView() {
        super.initView();
        mCategory = getIntent().getStringExtra(PARAM_SEARCH_CATEGORY);
        mTitle = getIntent().getStringExtra(PARAM_SEARCH_TITLE);

        isShowProgressDialog(true);
        searchShops();

        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);
                mList.clear();
                mCurrentPage = 1;
                searchShops();
            }
        });
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);

                searchShops();
            }
        });
    }

    @Override
    protected void reFrensh() {
        super.reFrensh();
        searchShops();
    }

    private void searchShops() {
        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("token", "");
        params1.put("page", mCurrentPage);
        params1.put("category", mCategory);
        params1.put("title", mTitle);
        HttpLoader.getInstance().getSearchShops(params1, mCompositeSubscription, new SubscriberCallBack<SearchShopEntity>(this,this){

            @Override
            protected void onSuccess(SearchShopEntity response) {
                super.onSuccess(response);

                if (response.getList().size() > 0) {
                    mCurrentPage++;
                    mList.addAll(response.getList());
                    mSearchShops.notifyDataSetChanged();
                    hideNoContentView();
                } else {
                    showNoContentView();
                }

            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mSrlRefresh.finishRefresh();
                mSrlRefresh.finishLoadMore();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }
        });
    }
}
