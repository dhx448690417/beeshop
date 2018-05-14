package com.beeshop.beeshop.activity;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ProgressControlInterface;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.AppManager;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.SystemBarTintManager;
import com.beeshop.beeshop.utils.ViewUtil;
import com.beeshop.beeshop.views.DialogFactory;
import com.beeshop.beeshop.views.StatusView;

import java.util.HashMap;
import java.util.List;

import rx.subscriptions.CompositeSubscription;


/**
 * Author : cooper
 * Time :  2017/12/28 上午11:23
 * Description : 基础类
 */

public class BaseActivity extends AppCompatActivity implements ProgressControlInterface, StatusView.ReloadCallBack {

    /**
     * 加载进度框
     */
    private Dialog mProgressDialog;
    private LinearLayout mLlInfoContainer;
    private TextView mTvInfoTitle;
    private int mScreenHeight;
    private int mShowDialogTimes; // 用于记录进度条显示次数，解决多次显示progressDialog，第一次隐藏后，还有借口请求没响应结束bug。

    /**
     * 是否显示进度对话框
     */
    protected boolean isShowProgressDialog = true;

    protected CompositeSubscription mCompositeSubscription; // 取消接口订阅

    protected SystemBarTintManager tintManager;

    private static long mAppBackgroundTime = -1;

    /**
     * 网络异常显示页面
     */
    protected StatusView mStatusView;
    private PowerManager mPowerManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        mProgressDialog = DialogFactory.createLoadingDialog(this);
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mShowDialogTimes = 0;
            }
        });
        mScreenHeight = ViewUtil.getScreenWidthAndHeight(this)[1];
        if (Build.VERSION.SDK_INT < 20) {
            setSystemBarStatus(R.color.systembar);
        }
        mCompositeSubscription = new CompositeSubscription();
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        AppManager.getAppManager().finishActivity(this);
        hideProgressDialog();
    }

    protected void setSystemBarStatus(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);  //设置上方状态栏颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //RXjava取消注册，以避免内存泄露
    private void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    private View view;
    /**
     * 帮助对话框
     */
//    public void showHelpDialog(String title, List<HelpBean> helpBeanList,boolean isControlHeight) {
//        if (mHelpInfoDialog == null) {
//            mHelpInfoDialog = DialogPlus.newDialog(this)
//                    .setContentHolder(new ViewHolder(R.layout.dialog_help_info))
//                    .setGravity(Gravity.BOTTOM)
//                    .setCancelable(true)
//                    .create();
//            view = mHelpInfoDialog.getHolderView();
//            mLlInfoContainer = view.findViewById(R.id.ll_info_container);
//            mTvInfoTitle = view.findViewById(R.id.tv_info_title);
//            view.findViewById(R.id.iv_info_close).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mHelpInfoDialog.dismiss();
//                }
//            });
//        }
//        mTvInfoTitle.setText(title);
//        if (mLlInfoContainer.getChildCount() > 0) {
//            mLlInfoContainer.removeAllViews();
//        }
//        if (helpBeanList.size() > 0) {
//            for (HelpBean itemsBean : helpBeanList) {
//                View view = LayoutInflater.from(this).inflate(R.layout.item_help_info, null);
//                TextView tvTitle = view.findViewById(R.id.tv_title);
//                TextView tvContent = view.findViewById(R.id.tv_content);
//                tvTitle.setText(itemsBean.getKey() + "：");
//                tvContent.setText(itemsBean.getValue());
//                mLlInfoContainer.addView(view);
//                mLlInfoContainer.invalidate();
//            }
//        }
//        if (isControlHeight) {
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mScreenHeight*2 / 5);
//            view.setLayoutParams(lp);
//        }
//        mHelpInfoDialog.show();
//    }

    /**
     * 设置页面邮箱水印
     * @param context
     * @return
     */
    protected Drawable setBackgroundShuiYinWithEmail(Context context, int width, int height) {
        String email = SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_EMAIL, "");
        return ViewUtil.setBackgroundShuiYin(context,width,height, email);
    }

    /**
     * 显示加载进度dialog
     */
    protected void showProgressDialog() {
        mShowDialogTimes++;
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏加载进度dialog
     */
    protected void hideProgressDialog() {
        if (mShowDialogTimes > 0) {
            mShowDialogTimes--;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing() && mShowDialogTimes == 0) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 设置title
     *
     * @param title
     */
    protected void setTitle(String title) {//设置title
        TextView head_center_tv = (TextView) findViewById(R.id.titleBar_title);
        head_center_tv.setText(title);
    }

    /**
     * 设置title和左上角返回事件
     */
    protected void setTitleAndBackPressListener(String title) {
        setTitle(title);
        findViewById(R.id.back_iv).setVisibility(View.VISIBLE);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置左上角返回事件
     * @param listener
     */
    protected void setBackPressListener(View.OnClickListener listener) {
        findViewById(R.id.back_iv).setVisibility(View.VISIBLE);
        findViewById(R.id.back_iv).setOnClickListener(listener);
    }

    /**
     * 设置右上角点击事件
     * @param listener
     */
    protected void setRightTextClickListener(View.OnClickListener listener) {
        findViewById(R.id.titleBar_right).setVisibility(View.VISIBLE);
        findViewById(R.id.titleBar_right).setOnClickListener(listener);
    }

    /**
     * 设置右上角点击事件(可以设置颜色、大小、内容)
     * @param listener
     */
    protected void setRightTextStyleAndClickListener(String content,int textSize, int color, View.OnClickListener listener) {
        TextView textView = (TextView) findViewById(R.id.titleBar_right);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        textView.setTextColor(color);
        textView.setText(content);
        setRightTextClickListener(listener);
    }


    /**
     * 设置titleBar背景
     * @param color
     */
    protected void setTitleBackgroud(int color){
        LinearLayout ll_root = (LinearLayout) findViewById(R.id.ll_root);
        ll_root.setBackgroundResource(color);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    /**
     * 显示网络异常页面
     */
    protected void showNoNetWork() {
        hideNoContentView();
        if (mStatusView == null) {
            mStatusView = (StatusView) findViewById(R.id.status_view);
        }
        mStatusView.setmReloadCallBack(this);
        mStatusView.setNoNetStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示网络异常页面 (StatusView在其他布局中时)
     */
    protected void showNoNetWork(View view) {
        hideNoContentView();
        if (mStatusView == null) {
            mStatusView = (StatusView) view.findViewById(R.id.status_view);
        }
        mStatusView.setmReloadCallBack(this);
        mStatusView.setNoNetStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示无数据view
     */
    protected void showNoContentView() {
        if (mStatusView == null) {
            mStatusView = (StatusView) findViewById(R.id.status_view);
        }
        mStatusView.setNoContentStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示无数据view (StatusView在其他布局中时)
     */
    protected void showNoContentView(View view) {
        if (mStatusView == null) {
            mStatusView = (StatusView) view.findViewById(R.id.status_view);
        }
        mStatusView.setNoContentStatus();
        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏无数据vbiew
     */
    protected void hideNoContentView() {
        if (mStatusView == null) {
            return;
        }
        mStatusView.setVisibility(View.GONE);
    }

    @Override
    public void reload() {
        mStatusView.setVisibility(View.GONE);
        reFrensh();
    }

    /**
     * 刷新页面
     */
    protected void reFrensh() {}


    @Override
    protected void onStop() {
        super.onStop();
        // 应用进入后台或者锁屏后
        if (!isAppOnForeground() || !mPowerManager.isScreenOn()) {
            // 记录应用进入后台时的时间
            mAppBackgroundTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 程序是否在前台运行
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }
}
