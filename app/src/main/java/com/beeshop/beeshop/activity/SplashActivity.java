package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Author : cooper
 * Time :  2017/12/28 上午11:24
 * Description : 启动页
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.launch_iv)
    ImageView launchIv;
    private Bundle mPushBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_splash_layout);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mPushBundle = getIntent().getExtras();
        }
        if (!isTaskRoot()) {// 解决部分7.0手机，第一次安装app，点击home键将app至于后台，再次点击桌面图标后，应用重新从启动页启动bug
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Build.VERSION.SDK_INT >= 23) {
//            // 检查权限
//            if (mPermissionsChecker.lacksPermissions(AppConfig.PERMISSIONS)) {
//                PermissionsActivity.startActivityForResult(this, 0, AppConfig.PERMISSIONS);
//            } else {
//                delayStartHome();
//            }
//        } else {
//            delayStartHome();
//        }
        delayStartHome();
    }

    private void delayStartHome() {
        launchIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                if (!TextUtils.isEmpty(SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""))) {
//
//                } else {
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                }
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
//            finish();
        }
    }


}
