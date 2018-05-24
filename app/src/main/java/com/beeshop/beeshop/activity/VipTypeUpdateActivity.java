package com.beeshop.beeshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.beeshop.beeshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : cooper
 * Time :  2018/5/24 上午10:36
 * Description : 添加/更新会员类别
 */
public class VipTypeUpdateActivity extends BaseActivity {

    @BindView(R.id.et_vip_type_name)
    EditText etVipTypeName;
    @BindView(R.id.et_sort_number)
    EditText etSortNumber;
    @BindView(R.id.et_vip_type_introduce)
    EditText etVipTypeIntroduce;
    @BindView(R.id.tv_introduce_number)
    TextView tvIntroduceNumber;
    @BindView(R.id.tv_update)
    TextView tvUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_vip_type_update);
        ButterKnife.bind(this);
        setTitleAndBackPressListener("会员类别更新");

        initView();
    }

    private void initView() {
        etVipTypeIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvIntroduceNumber.setText(s.length()+"/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
