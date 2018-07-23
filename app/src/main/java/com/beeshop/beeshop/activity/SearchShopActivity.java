package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.model.ShopCategoryEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：cooper
 * Time：  2018/5/20 上午10:39
 * Description：
 */
public class SearchShopActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.fl_tag_shop_type)
    TagFlowLayout flTagShopType;

    private String mCategory = "";
    private String mTitle;
    private ShopCategoryEntity mShopCategoryEntity;
    private TagAdapter mTagAdapter;

    private List<String> mTags = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_shop);
        ButterKnife.bind(this);

        initView();
        getShopCategory();
    }

    private void initView() {

        flTagShopType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (TextUtils.equals(mCategory, mShopCategoryEntity.getList().get(position).getId() + "")) {
                    mCategory = "";
                } else {
                    mCategory = mShopCategoryEntity.getList().get(position).getId() + "";
                }
                return false;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_search:
                String title = etSearch.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showToast("请输入要搜索的内容");
                    return;
                }
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtra(SearchResultActivity.PARAM_SEARCH_CATEGORY, mCategory);
                intent.putExtra(SearchResultActivity.PARAM_SEARCH_TITLE, title);
                startActivity(intent);
                break;
        }
    }

    private void getShopCategory() {
        HashMap<String, Object> params1 = new HashMap<>();
//        params1.put("category", "");
        HttpLoader.getInstance().getShopCategory(params1, mCompositeSubscription, new SubscriberCallBack<ShopCategoryEntity>(this, this) {

            @Override
            protected void onSuccess(ShopCategoryEntity response) {
                super.onSuccess(response);
                mShopCategoryEntity = response;
                for (ShopCategoryEntity.ListBean listBean : response.getList()) {
                    mTags.add(listBean.getName());
                }

                mTagAdapter = new TagAdapter<String>(mTags) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = new TextView(SearchShopActivity.this);
                        tv.setBackgroundResource(R.drawable.selector_tag_bg);
                        tv.setTextColor(ContextCompat.getColor(SearchShopActivity.this,R.color.tag_text_color));
                        tv.setTextSize(20);
                        tv.setText(s);
                        return tv;
                    }

                };
                flTagShopType.setAdapter(mTagAdapter);
                flTagShopType.setMaxSelectCount(1);
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
            }
        });
    }
}
