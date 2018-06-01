package com.beeshop.beeshop.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;

/**
 * Author : Cooper
 * Time : 2017/3/2  16:12
 * Description : 无网络view
 */

public class StatusView extends RelativeLayout {

    private Context mContext;
    private View mRootView;
    private ReloadCallBack mReloadCallBack;
    private TextView mInfoTv;
    private ImageView mInfoIv;
    private TextView mReloadTv;

    private int mInfoImage;
    private String mInfoText;

    public void setmReloadCallBack(ReloadCallBack mReloadCallBack) {
        this.mReloadCallBack = mReloadCallBack;
    }

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatusView);
        mInfoImage = typedArray.getResourceId(R.styleable.StatusView_infoImage,R.drawable.no_data);
        mInfoText = typedArray.getString(R.styleable.StatusView_infoText);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.no_net_layout,null);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mInfoIv = (ImageView) mRootView.findViewById(R.id.info_iv);
        mInfoTv = (TextView) mRootView.findViewById(R.id.info_tv);
        mReloadTv = (TextView) mRootView.findViewById(R.id.tv_reload);

        addView(mRootView,layoutParams);
//        this.setBackgroundColor(mContext.getResources().getColor(R.color.grey_F3F3F3));
    }

    /**
     * 无网络状态
     */
    public void setNoNetStatus() {
        mInfoTv.setText("数据加载失败，点击页面重新加载。");
        mInfoIv.setBackgroundResource(R.drawable.net_error);
//        mReloadTv.setVisibility(VISIBLE);
//        mReloadTv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mReloadCallBack.reload();
//            }
//        });
        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mReloadCallBack.reload();
            }
        });
        invalidate();
    }

    /**
     * 无数据状态
     */
    public void setNoContentStatus() {
        mInfoTv.setText(mInfoText);
        mInfoIv.setBackgroundResource(mInfoImage);
        mRootView.setOnClickListener(null);
        mReloadTv.setVisibility(GONE);
        invalidate();
    }

    public interface ReloadCallBack{
        void reload();
    }
}
