package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.MyOrderListAdapter;
import com.beeshop.beeshop.adapter.ShopOrderListAdapter;
import com.beeshop.beeshop.model.OrderListEntity;
import com.beeshop.beeshop.model.SearchShopEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.MaterialDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：产品订单列表
 */
public class ShopOrderListActivity extends BaseListActivity<OrderListEntity.ListBean>  {

    private ShopOrderListAdapter mShopOrderListAdapter;
    private int mCurrentPage = 1;
    private MaterialDialog modifyPriceDialog;
    private EditText etPrice;

    private OrderListEntity.ListBean mOrderBean;
    private String mMoney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("产品订单");


    }

    @Override
    protected int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mShopOrderListAdapter = new ShopOrderListAdapter(this,mList);
        mShopOrderListAdapter.setConfirmCallBack(new ShopOrderListAdapter.ConfirmCallBack() {
            @Override
            public void clickConfirm(int type, int position) {
                mOrderBean = mList.get(position);
                if (type == 1) {
                    modifyPriceDialog.show();
                } else if (type == 2) {
                    setOrderState();
                }

            }
        });
        return mShopOrderListAdapter;
    }

    @Override
    protected void initView() {
        super.initView();

        mSrlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);
                mList.clear();
                mCurrentPage = 1;
                getShopOrderList();
            }
        });
        mSrlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isShowProgressDialog(false);

                getShopOrderList();
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_price_modify_content, new LinearLayout(this), false);
        etPrice = view.findViewById(R.id.et_modify_price);
        modifyPriceDialog = new MaterialDialog(this)
                .setTitle("修改订单价格")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMoney = etPrice.getText().toString();
                        if (TextUtils.isEmpty(mMoney)) {
                            ToastUtils.showToast("请填写价格");
                            return;
                        }
                        modifyPrice();
                        modifyPriceDialog.dismiss();

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modifyPriceDialog.dismiss();

                    }
                })
                .setContentView(view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mSrlRefresh.autoRefresh();
    }

    /**
     * 获取店铺订单别表
     */
    private void getShopOrderList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("page", mCurrentPage);
        HttpLoader.getInstance().getShopOrderList(params1, mCompositeSubscription, new SubscriberCallBack<OrderListEntity>(this, this) {


            @Override
            protected void onSuccess(OrderListEntity response) {
                super.onSuccess(response);

                if (mCurrentPage == 1) {
                    mList.clear();
                }

                mCurrentPage++;
                mList.addAll(response.getList());
                mShopOrderListAdapter.notifyDataSetChanged();

                if (mList.size() > 0) {
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


    /**
     * 修改价格
     *
     */
    private void modifyPrice() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("order_id", mOrderBean.getId());
        params.put("money", mMoney);
        HttpLoader.getInstance().modifyPrice(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                mOrderBean.setReal_payment(mMoney);
                etPrice.setText("");
                mShopOrderListAdapter.notifyDataSetChanged();
                ToastUtils.showToast("修改价格成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 设置订单为发货状态
     *
     */
    private void setOrderState() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("order_id", mOrderBean.getId());
        HttpLoader.getInstance().setOrderState(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                mOrderBean.setStatus(3);
                mShopOrderListAdapter.notifyDataSetChanged();
                ToastUtils.showToast("设置成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
