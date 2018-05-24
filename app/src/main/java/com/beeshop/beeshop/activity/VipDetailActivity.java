package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.RecordAdapter;
import com.beeshop.beeshop.views.ListViewForScrollView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

    private RecordAdapter mRecordAdapter;
    private List<String> mRecordList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_detail);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("编辑会员");

        initView();
    }

    private void initView() {
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        nsVipType.attachDataSource(dataset);

        mRecordList.add("");
        mRecordList.add("");
        mRecordList.add("");
        mRecordList.add("");
        mRecordList.add("");

        mRecordAdapter = new RecordAdapter(this, mRecordList);
        lvsvRecord.setAdapter(mRecordAdapter);

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
                break;
            case R.id.tv_recharge:
                break;
            case R.id.tv_pay:
                mRecordList.add("");
                mRecordList.add("");
                mRecordList.add("");
                mRecordAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_recharge_record:
                tvPayRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                vRechargeRecord.setVisibility(View.VISIBLE);
                vPayRecord.setVisibility(View.GONE);
                break;
            case R.id.rl_pay_record:
                tvPayRecord.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvRechargeRecord.setTextColor(getResources().getColor(R.color.text_color_black));
                vRechargeRecord.setVisibility(View.GONE);
                vPayRecord.setVisibility(View.VISIBLE);
                break;
        }
    }

}
