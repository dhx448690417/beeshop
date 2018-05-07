package com.beeshop.beeshop.net;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * User: 孙伟力
 * Date: 15/11/4
 * Time: 下午2:49
 */
public class PhoneInfoHelper {
    public static String osv;
    public static String netType;
    public static String platform;
    public static String IMEI;

    /**
     * 产品线 id，例如 Android 平台赶集生活客户端的产品线 id 为 801
     */
    public static String customerId = "879";
    /**
     * 当前应用程序的 Application Context
     */
    public static Application appContext;
    /**
     * 当前的版本号，比如：6.0.0
     */
    public static String versionName;
    /**
     * 当前设备的屏幕宽度（短边）所占有的像素数
     */
    public static int screenWidth;
    /**
     * 当前设备的屏幕高度（长边）所占有的像素数
     */
    public static int screenHeight;
    /**
     * 屏幕的密度参数，如：0.75, 1.0, 1.5, 2.0, 3.0 等
     */
    public static float density;
    /**
     * 设备的型号
     */
    public static String model;

    public static void init(Application context) {
        appContext = context;
        try {
            PackageInfo packInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            versionName = "unknown";
        }

        IMEI = DeviceId.get(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        density = displayMetrics.density;
        model = Build.MODEL == null ? "unknown" : Build.MODEL;
        osv = Build.VERSION.RELEASE == null ? "unknown" : Build.VERSION.RELEASE;
        netType = getAPNType();
        platform = Build.CPU_ABI == null ? "unknown" : Build.CPU_ABI;
    }

    public static String getAPNType() {
        String netType = "none";
        if (appContext != null) {
            ConnectivityManager manager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return netType;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = "wifi";
            } else if (nType == ConnectivityManager.TYPE_MOBILE) {
                int nSubType = networkInfo.getSubtype();
                TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming()) {
                    netType = "4G";
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                        || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = "3G";
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                        || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                        || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = "2G";
                } else {
                    netType = "2G";
                }
            } else if (nType == ConnectivityManager.TYPE_WIMAX) {
                netType = "WIMAX";
            } else if (nType == ConnectivityManager.TYPE_ETHERNET) {
                netType = "ETHERNET";
            } else if (nType == ConnectivityManager.TYPE_BLUETOOTH) {
                netType = "BLUETOOTH";
            } else if (nType == ConnectivityManager.TYPE_VPN) {
                netType = "VPN";
            } else if (nType == ConnectivityManager.TYPE_DUMMY) {
                netType = "DUMMY";
            } else if (nType == ConnectivityManager.TYPE_MOBILE_DUN) {
                netType = "MOBILE_DUN";
            } else {
                netType = "other";
            }
        }
        return netType;
    }

    public static boolean isNetworkConnected() {
        if (appContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
