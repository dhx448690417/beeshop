package com.beeshop.beeshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;


import com.beeshop.beeshop.application.BeeShopApplication;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Utils {
    public static final String TAG = "PushActivity";
    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";

    public static String logStringCache = "";

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(ctx.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (Exception e) {
        }

        return resultData;
    }

    // 获取ApiKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    // 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
    public static boolean hasBind(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String flag = sp.getString("bind_flag", "");
        if ("ok".equalsIgnoreCase(flag)) {
            return true;
        }
        return false;
    }

    public static void setBind(Context context, boolean flag) {
        String flagStr = "not";
        if (flag) {
            flagStr = "ok";
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("bind_flag", flagStr);
        editor.commit();
    }

    /**
     * 没有用到？
     *
     * @param originalText
     * @return
     */

    public static List<String> getTagsList(String originalText) {
        if (originalText == null || originalText.equals("")) {
            return null;
        }
        List<String> tags = new ArrayList<String>();
        int indexOfComma = originalText.indexOf(',');
        String tag;
        while (indexOfComma != -1) {
            tag = originalText.substring(0, indexOfComma);
            tags.add(tag);

            originalText = originalText.substring(indexOfComma + 1);
            indexOfComma = originalText.indexOf(',');
        }

        tags.add(originalText);
        return tags;
    }

    public static String getLogText(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString("log_text", "");
    }

    public static void setLogText(Context context, String text) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("log_text", text);
        editor.commit();
    }

    /**
     * 处理背景图片的一些工具，如放大 缩小...
     */

    /**
     * 使背景图片适应手机屏幕分辨率
     *
     * @param context      上下文环境
     * @param screenWidth  手机屏幕宽度
     * @param screenHeight 手机屏幕高度
     * @param resId        背景图片资源ID
     * @return 返回一个新的适应手机屏幕分辨率的图片
     */
    public static Bitmap getSuitableBg(Context context, int screenWidth,
                                       int screenHeight, int resId) {
        Bitmap bg = BitmapFactory.decodeResource(context.getResources(), resId);
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        float scaleWidth = (float) screenWidth / bgWidth;
        float scaleHeight = (float) screenHeight / bgHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBg = Bitmap.createBitmap(bg, 0, 0, bgWidth, bgHeight, matrix,
                true);
        return newBg;

    }

    public static String getVersion() {
        try {
            PackageManager manager = BeeShopApplication.getInstance().getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BeeShopApplication.getInstance().getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode() {

        try {
            PackageManager manager = BeeShopApplication.getInstance().getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BeeShopApplication.getInstance().getApplicationContext().getPackageName(),
                    0);
            int version = info.versionCode;

            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    public static void main(String[] args) {
        int a = 70;
        double value = a / 100.00;
        double result = 520.0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println(decimalFormat.format(result));
    }


    /**
     * 格式化金额的显示
     *
     * @param str
     * @return
     */
    public static String handleString(String str) {

        String result = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                int count = 0;
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c == '0') count++;
                }
                int amount = Integer.parseInt(str);
                String unit = "";
                if (count >= 4) {
                    amount = amount / 10000;
                    unit = "万元";
                } else {
                    unit = "元";
                }
//                switch (count) {
//                    case 0:
//                        unit = "元";
//                    case 1:
//                        break;
//                    case 2:
//                        amount = amount / 100;
//                        unit = "百";
//                        break;
//                    case 3:
//                        amount = amount / 1000;
//                        unit = "千";
//                        break;
//                    case 4:
//                    default:
//                        amount = amount / 10000;
//                        unit = "万";
//                }
                result = StringTools.formatAmountStr(String.valueOf(amount)) + unit;
            }
            if ("".equals(result)) result = "0元";
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 格式化金额的显示
     *
     * @param str
     * @return
     */
    public static String handleHint(String str) {

        String result = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                int count = 0;
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c == '0') count++;
                }
                int amount = Integer.parseInt(str);
                String unit = "";
                switch (count) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        amount = amount / 100;
                        unit = "百元";
                        break;
                    case 3:
                        amount = amount / 1000;
                        unit = "千元";
                        break;
                    case 4:
                    default:
                        amount = amount / 10000;
                        unit = "万元";
                }
                result = StringTools.formatAmountStr(String.valueOf(amount)) + unit;
            }
            if ("".equals(result)) result = "0元";
        } catch (Exception e) {
        }
        return result;
    }

    private static long lastClickTime = 0;

    /**
     * 防止按钮连续点击
     * 用法：if (Utils.isFastClick()) return ;
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getMoney(String money) {
        String money_o = money;
        String money_f;
        if (money_o.length() > 3 && money_o.contains(".")) {
            money_f = money_o.substring(0, money_o.indexOf(".") + 3);
        } else {
            money_f = money_o;
        }
        return money_f;
    }

    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }

    /**
     * 得到从现在开始几天后的时间
     * @param day
     * @return
     */
    public static String getNowDateAfter(int day) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format=new SimpleDateFormat("yyyy年M月d日");
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return format.format(now.getTime());
    }
}
