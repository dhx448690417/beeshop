package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.MyVipDetailAdapter;
import com.beeshop.beeshop.model.JoinedVipEntity;
import com.beeshop.beeshop.model.JoinedVipRecordEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author：cooper
 * Time：  2018/5/19 上午10:47
 * Description：
 */
public class MyVipDetailActivity extends BaseActivity {

    public static final String PARAM_MY_VIP_DETAIL_ID = "param_my_vip_detail_id";
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_join_time)
    TextView tvJoinTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_recharge_record)
    TextView tvRechargeRecord;
    @BindView(R.id.v_recharge_record)
    View vRechargeRecord;
    @BindView(R.id.rl_recharge_record)
    RelativeLayout rlRechargeRecord;
    @BindView(R.id.tv_pay_record)
    TextView tvPayRecord;
    @BindView(R.id.v_pay_record)
    View vPayRecord;
    @BindView(R.id.rl_pay_record)
    RelativeLayout rlPayRecord;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private JoinedVipRecordEntity mJoinedVipRecordEntity;

    private List<JoinedVipRecordEntity.ListBean> mRechargeRecordList = new ArrayList<>();
    private List<JoinedVipRecordEntity.ListBean> mConsumeRecordList = new ArrayList<>();

    private MyVipDetailAdapter mRechargeRecordAdapter;
    private MyVipDetailAdapter mConsumeRecordAdapter;


    private MaterialDialog ConfirmDialog;
    private JoinedVipEntity.ListBean mJoinedVipBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_my_vip_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("我的会员详情");
        mJoinedVipBean = (JoinedVipEntity.ListBean) getIntent().getSerializableExtra(PARAM_MY_VIP_DETAIL_ID);
        initView();
    }

    protected void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);

        tvShopName.setText(mJoinedVipBean.getShop_title());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(mJoinedVipBean.getCreated_time()*1000);
        Date date = c.getTime();
        tvJoinTime.setText(sdf.format(date));


        tvMoney.setText(mJoinedVipBean.getMoney() + "元");

        mRechargeRecordAdapter = new MyVipDetailAdapter(this, mRechargeRecordList);
        mRechargeRecordAdapter.setmConfirmClickCallBack(new MyVipDetailAdapter.ConfirmClickCallBack() {
            @Override
            public void confirmClick(int postion) {
                ConfirmDialog.show();
            }
        });

        mConsumeRecordAdapter = new MyVipDetailAdapter(this, mConsumeRecordList);
        mConsumeRecordAdapter.setmConfirmClickCallBack(new MyVipDetailAdapter.ConfirmClickCallBack() {
            @Override
            public void confirmClick(int postion) {
                ConfirmDialog.show();
            }
        });

        ConfirmDialog = new MaterialDialog(this)
                .setTitle("确认订单")
                .setMessage("请仔细核对订单金额及内容，确认后将从对应会员记录中扣除订单金额。")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfirmDialog.dismiss();

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfirmDialog.dismiss();

                    }
                });

        getMyJoinedVipConsumeRecordList();
        getMyJoinedVipRechargeRecordList();

    }

    @OnClick({R.id.rl_recharge_record, R.id.rl_pay_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_recharge_record:
                rvList.setAdapter(mRechargeRecordAdapter);

                tvPayRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                vRechargeRecord.setVisibility(View.VISIBLE);
                vPayRecord.setVisibility(View.GONE);
                break;
            case R.id.rl_pay_record:
                rvList.setAdapter(mConsumeRecordAdapter);

                tvPayRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                vRechargeRecord.setVisibility(View.GONE);
                vPayRecord.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 获取消费记录列表
     */
    private void getMyJoinedVipConsumeRecordList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("member_id", mJoinedVipBean.getId());
        showProgress();
        HttpLoader.getInstance().getMyJoinedVipConsumeRecordList(params1, mCompositeSubscription, new SubscriberCallBack<JoinedVipRecordEntity>() {

            @Override
            protected void onSuccess(JoinedVipRecordEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    hideNoContentView();
                    mConsumeRecordList.clear();
                    mConsumeRecordList.addAll(response.getList());
                    mConsumeRecordAdapter.notifyDataSetChanged();
                } else {
                    showNoContentView();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                hideProgress();
            }
        });
    }


    /**
     * 获取消费记录列表
     */
    private void getMyJoinedVipRechargeRecordList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        params1.put("member_id", mJoinedVipBean.getId());
        showProgress();
        HttpLoader.getInstance().getMyJoinedVipRechargeRecordList(params1, mCompositeSubscription, new SubscriberCallBack<JoinedVipRecordEntity>() {

            @Override
            protected void onSuccess(JoinedVipRecordEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    hideNoContentView();
                    mRechargeRecordList.clear();
                    mRechargeRecordList.addAll(response.getList());
                    mRechargeRecordAdapter.notifyDataSetChanged();
                } else {
                    showNoContentView();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                hideProgress();
            }

        });
    }

}
