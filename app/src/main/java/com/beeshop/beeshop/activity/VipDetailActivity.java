package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.RecordAdapter;
import com.beeshop.beeshop.model.VipMoneyHistoryRecord;
import com.beeshop.beeshop.model.VipEntity;
import com.beeshop.beeshop.model.VipTypeEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.ListViewForScrollView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : cooper
 * Time :  2018/5/24 下午2:52
 * Description :
 */
public class VipDetailActivity extends BaseActivity {


    @BindView(R.id.et_vip_name)
    EditText etVipName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_vip_pay)
    TextView tvVipPay;
    @BindView(R.id.v_vip_pay)
    View vVipPay;
    @BindView(R.id.tv_vip_recharge)
    TextView tvVipRecharge;
    @BindView(R.id.v_vip_recharge)
    View vVipRecharge;
    @BindView(R.id.tv_vip_edit)
    TextView tvVipEdit;
    @BindView(R.id.v_vip_edit)
    View vVipEdit;
    @BindView(R.id.ns_vip_type)
    NiceSpinner nsVipType;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.ll_vip_update)
    LinearLayout llVipUpdate;
    @BindView(R.id.et_recharge)
    EditText etRecharge;
    @BindView(R.id.et_recharge_explain)
    EditText etRechargeExplain;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.ll_vip_recharge)
    LinearLayout llVipRecharge;
    @BindView(R.id.et_pay)
    EditText etPay;
    @BindView(R.id.et_pay_explain)
    EditText etPayExplain;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.ll_vip_pay)
    LinearLayout llVipPay;
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
    @BindView(R.id.lvsv_record)
    ListViewForScrollView lvsvRecord;

    public static final String PARAM_KEY_VIP_MEMBER = "param_key_vip_member";

    private RecordAdapter mRecordAdapter;
    private List<VipMoneyHistoryRecord.ListBean> mRecordList = new ArrayList<>();
    private List<VipMoneyHistoryRecord.ListBean> mRechargeRecordList = new ArrayList<>();
    private List<VipMoneyHistoryRecord.ListBean> mPayRecordList = new ArrayList<>();
    protected List<VipTypeEntity.ListBean> mVipTypeList = new ArrayList<>();
    private int mMemberClassId;

    private String mRemark;
    private String mVipName;
    private String mRechargeMoney;
    private String mRechargeExplan;
    private String mPayMoney;
    private String mPayExplan;
    private VipEntity.ListBean mVipEntity;

    private boolean isShowPayList; // true显示消费列表    false显示充值列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("编辑会员");
        mVipEntity = (VipEntity.ListBean) getIntent().getSerializableExtra(PARAM_KEY_VIP_MEMBER);
        if (mVipEntity != null) {
            mMemberClassId = mVipEntity.getMember_class_id();
            initView();
        }
    }

    private void initView() {
        nsVipType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMemberClassId = mVipTypeList.get(position).getId();
            }
        });
        etPhoneNumber.setText(mVipEntity.getPhone());
        etRemark.setText(mVipEntity.getRemark());
        mRecordAdapter = new RecordAdapter(this, mRecordList);
        mRecordAdapter.setRecordType(0);
        lvsvRecord.setAdapter(mRecordAdapter);
        getVipTypeList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRechargeHistoryRecord();
        getPayHistoryRecord();
    }

    @OnClick({R.id.rl_vip_pay, R.id.rl_vip_recharge, R.id.rl_vip_update, R.id.tv_update, R.id.tv_recharge, R.id.tv_pay,R.id.rl_recharge_record, R.id.rl_pay_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_vip_pay:
                tvVipPay.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvVipRecharge.setTextColor(getResources().getColor(R.color.text_color_black));
                tvVipEdit.setTextColor(getResources().getColor(R.color.text_color_black));
                vVipPay.setVisibility(View.VISIBLE);
                llVipPay.setVisibility(View.VISIBLE);
                vVipRecharge.setVisibility(View.GONE);
                llVipRecharge.setVisibility(View.GONE);
                vVipEdit.setVisibility(View.GONE);
                llVipUpdate.setVisibility(View.GONE);

                break;
            case R.id.rl_vip_recharge:

                tvVipPay.setTextColor(getResources().getColor(R.color.text_color_black));
                tvVipRecharge.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvVipEdit.setTextColor(getResources().getColor(R.color.text_color_black));
                vVipPay.setVisibility(View.GONE);
                llVipPay.setVisibility(View.GONE);
                vVipRecharge.setVisibility(View.VISIBLE);
                llVipRecharge.setVisibility(View.VISIBLE);
                vVipEdit.setVisibility(View.GONE);
                llVipUpdate.setVisibility(View.GONE);
                break;
            case R.id.rl_vip_update:

                tvVipPay.setTextColor(getResources().getColor(R.color.text_color_black));
                tvVipRecharge.setTextColor(getResources().getColor(R.color.text_color_black));
                tvVipEdit.setTextColor(getResources().getColor(R.color.colorPrimary));
                vVipPay.setVisibility(View.GONE);
                llVipPay.setVisibility(View.GONE);
                vVipRecharge.setVisibility(View.GONE);
                llVipRecharge.setVisibility(View.GONE);
                vVipEdit.setVisibility(View.VISIBLE);
                llVipUpdate.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_update:
                mRemark = etRemark.getText().toString();
                if (TextUtils.isEmpty(mRemark)) {
                    ToastUtils.showToast("备注不能为空");
                    return;
                }
                updateVip();
                break;
            case R.id.tv_recharge:
                if (verifyRecharge()) {
                    recharge();
                }
                break;
            case R.id.tv_pay:
                if (verifyPay()) {
                    pay();
                }
                break;
            case R.id.rl_recharge_record:
                isShowPayList = false;
                mRecordList.clear();
                mRecordList.addAll(mRechargeRecordList);
                mRecordAdapter.setRecordType(1);
                mRecordAdapter.notifyDataSetChanged();

                tvPayRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                vRechargeRecord.setVisibility(View.VISIBLE);
                vPayRecord.setVisibility(View.GONE);
                break;
            case R.id.rl_pay_record:
                isShowPayList = true;
                mRecordList.clear();
                mRecordList.addAll(mPayRecordList);
                mRecordAdapter.setRecordType(0);
                mRecordAdapter.notifyDataSetChanged();

                tvPayRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                vRechargeRecord.setVisibility(View.GONE);
                vPayRecord.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean verifyRecharge() {
        mRechargeExplan = etRechargeExplain.getText().toString();
        mRechargeMoney = etRecharge.getText().toString();

        if ( TextUtils.isEmpty(mRechargeMoney)) {
            ToastUtils.showToast("请输入充值金额");
            return false;
        } else if(TextUtils.isEmpty(mRechargeExplan)) {
            ToastUtils.showToast("请输入充值说明");
            return false;
        }
        return true;
    }

    private boolean verifyPay() {
        mPayExplan = etPayExplain.getText().toString();
        mPayMoney = etPay.getText().toString();

        if ( TextUtils.isEmpty(mPayMoney)) {
            ToastUtils.showToast("请输入消费金额");
            return false;
        } else if(TextUtils.isEmpty(mPayExplan)) {
            ToastUtils.showToast("请输入消费说明");
            return false;
        }
        return true;
    }

    /**
     * 更新会员
     *
     */
    private void updateVip() {  // todo  更新完一级会员 不见了  15210405105
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("member_class_id", mMemberClassId);
        params.put("user_id", mVipEntity.getUser_id());
        params.put("remark", mRemark);
        params.put("member_id", mVipEntity.getId());
        HttpLoader.getInstance().updateVip(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("更新会员成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 充值
     *
     */
    private void recharge() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("member_id", mVipEntity.getId());
        params.put("user_id", mVipEntity.getUser_id());
        params.put("money", mRechargeMoney);
        params.put("describe", mRechargeExplan);
//        params.put("give", "");
        HttpLoader.getInstance().recharge(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                getRechargeHistoryRecord();
                ToastUtils.showToast("充值成功");
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 消费
     *
     */
    private void pay() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("money", mPayMoney);
        params.put("user_id", mVipEntity.getUser_id());
        params.put("member_id", mVipEntity.getId());
        params.put("describe", mPayExplan);
        HttpLoader.getInstance().pay(params, mCompositeSubscription, new SubscriberCallBack(this, this) {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                ToastUtils.showToast("消费成功");
                getPayHistoryRecord();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }

    /**
     * 获取消费记录
     */
    private void getPayHistoryRecord() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        params.put("user_id", mVipEntity.getUser_id());
        params.put("member_id", mVipEntity.getId());
        HttpLoader.getInstance().payHistoryRecord(params, mCompositeSubscription, new SubscriberCallBack<VipMoneyHistoryRecord>(this,this){

            @Override
            protected void onSuccess(VipMoneyHistoryRecord response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mPayRecordList.clear();
                    mPayRecordList.addAll(response.getList());
                    mRecordList.clear();
                    if (isShowPayList) {
                        mRecordList.addAll(mPayRecordList);
                    } else {
                        mRecordList.addAll(mRechargeRecordList);
                    }
                    mRecordAdapter.setRecordType(0);
                    mRecordAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    showNoContentView();
                }
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

    /**
     * 获取充值记录
     */
    private void getRechargeHistoryRecord() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        params.put("user_id", mVipEntity.getUser_id());
        params.put("member_id", mVipEntity.getId());
        HttpLoader.getInstance().rechargeHistoryRecord(params, mCompositeSubscription, new SubscriberCallBack<VipMoneyHistoryRecord>(this,this){

            @Override
            protected void onSuccess(VipMoneyHistoryRecord response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mRechargeRecordList.clear();
                    mRechargeRecordList.addAll(response.getList());
                    mRecordList.clear();
                    if (isShowPayList) {
                        mRecordList.addAll(mPayRecordList);
                    } else {
                        mRecordList.addAll(mRechargeRecordList);
                    }
                    mRecordAdapter.setRecordType(1);
                    mRecordAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    showNoContentView();
                }
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

    private void getVipTypeList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        HttpLoader.getInstance().getVipTypeList(params1, mCompositeSubscription, new SubscriberCallBack<VipTypeEntity>(this, this) {

            @Override
            protected void onSuccess(VipTypeEntity response) {
                super.onSuccess(response);
                if (response.getList().size() > 0) {
                    mVipTypeList.addAll(response.getList());
                    List<String> dataset = new ArrayList<>();
                    int selectIndex = 0;
                    for (int i = 0; i < response.getList().size(); i++) {
                        VipTypeEntity.ListBean listBean = response.getList().get(i);
                        if (listBean.getId() == mMemberClassId) { // 记录默认选择哪个会员类别
                            selectIndex = i;
                        }
                        dataset.add(listBean.getTitle());
                    }
                    nsVipType.attachDataSource(dataset);
                    nsVipType.setSelectedIndex(selectIndex);
                }
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                if (errorBean.getCode() == 410) {
                    VipDetailActivity.this.finish();
                }
                ToastUtils.showToast(errorBean.getMsg());
            }

            @Override
            protected void onNetFailure(ResponseEntity errorBean) {
                super.onNetFailure(errorBean);
                showNoNetWork();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

}
