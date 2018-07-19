package com.beeshop.beeshop.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.MainActivity;
import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.utils.DeviceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Author : cooper
 * Time :  2017/12/27 下午6:59
 * Description :
 */

public class BeeShopApplication extends Application {

    private static BeeShopApplication mInstance;
    private static Context mContext;


    public static Context getContext() {
        return mContext;
    }

    public static BeeShopApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
