package com.beeshop.beeshop.utils;

import android.app.Activity;
import android.content.Context;

import com.beeshop.beeshop.activity.MainActivity;

import java.util.Stack;

/**
 * Activity 管理类
 *
 * @author Jason
 * @date 2014-8-8
 * @since 1.0
 */
public class AppManager {

    private static Stack<Activity> activityStack = new Stack<>();
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        //if (activityStack == null) {
        //    activityStack = new Stack<Activity>();
        //}
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void findFinishActivity(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<Activity>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                tempActivityStack.add(activity);
                //finishActivity(activity, tempActivityStack);

                if (null != activity) {
                    activity.finish();
                    activity = null;
                }
            }
        }
        activityStack.removeAll(tempActivityStack);
    }

    private void finishActivity(Activity activity, Stack<Activity> tempActivityStack) {

        if (activity != null) {
            activity.finish();
            activityStack.removeAll(tempActivityStack);
            activity = null;
        }
    }

    public void finishAllActivity() {
            LogUtil.i(" activityStack.size()-->>" + activityStack.size());
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
    }

    /**
     * 判断Activity是否存在
     * @param cls
     * @return
     */
    public boolean checkThisActivityExist(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭非main的所有activity
     */
    public void finishOtherAllActivity() {

        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (!activity.getClass().equals(MainActivity.class) && null != activity) {
                activityStack.get(i).finish();
                activity = null;
            }

        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断app是否已经打开
     * @return
     */
    public boolean checkAppExist() {
        return activityStack.size()>0;
    }
}
