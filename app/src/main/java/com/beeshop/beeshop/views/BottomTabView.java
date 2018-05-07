package com.beeshop.beeshop.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;


/**
 * Author : Cooper
 * Time : 2017/5/4  17:21
 * Description : MainActivity 底部tab的item
 */

public class BottomTabView extends RelativeLayout {

    private Context mContext;
    private View mView;
    private ImageView ivTabBottom;
    private TextView tvTabBottom;
    private View redPoint;

    private int mTabNormalImageRes;
    private int mTabSelectedImageRes;
    private int mTabNormalNewMessageImageRes;
    private int mTabSelectedNewMessageImageRes;
    private String mTabName;

    public BottomTabView(Context context) {
        super(context);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomTabView(Context context, int tabNormalImageRes, int tabSelectedImageRes, String tabName) {
        super(context);
        this.mTabNormalImageRes = tabNormalImageRes;
        this.mTabSelectedImageRes = tabSelectedImageRes;
        this.mTabName = tabName;
        init(context);
    }

    public BottomTabView(Context context, int tabNormalImageRes, int tabSelectedImageRes, int tabNormalNewMessageImageRes, int tabSelectedNewMessageImageRes, String tabName) {
        super(context);
        this.mTabNormalImageRes = tabNormalImageRes;
        this.mTabSelectedImageRes = tabSelectedImageRes;
        this.mTabNormalNewMessageImageRes = tabNormalNewMessageImageRes;
        this.mTabSelectedNewMessageImageRes = tabSelectedNewMessageImageRes;
        this.mTabName = tabName;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.item_main_bottom_tab_layout,this,false);
        ivTabBottom = (ImageView) mView.findViewById(R.id.iv_tab_bottom);
        tvTabBottom = (TextView) mView.findViewById(R.id.tv_tab_bottom);
        redPoint =  mView.findViewById(R.id.red_point);
        ivTabBottom.setBackgroundResource(mTabNormalImageRes);
        tvTabBottom.setText(mTabName);
        addView(mView);
    }

    /**
     * 设置是否显示红圆点
     * @param visible
     */
    public void setRedPointVisible(int visible) {
        redPoint.setVisibility(visible);
    }

    /**
     * 设置tab选中状态
     * @param isSelected
     */
    public void setTabStatus(boolean isSelected) {
        if (isSelected) {
            ivTabBottom.setBackgroundResource(mTabSelectedImageRes);
            tvTabBottom.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_color_selected));
        } else {
            ivTabBottom.setBackgroundResource(mTabNormalImageRes);
            tvTabBottom.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_color_normal));
        }
    }

    /**
     * 设置tab选中状态 (有红点)
     * @param isSelected
     */
    public void setTabStatusWidthNewMessage(boolean isSelected) {
        if (isSelected) {
            ivTabBottom.setBackgroundResource(mTabSelectedNewMessageImageRes);
            tvTabBottom.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_color_selected));
        } else {
            ivTabBottom.setBackgroundResource(mTabNormalNewMessageImageRes);
            tvTabBottom.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_color_normal));
        }
    }
}
