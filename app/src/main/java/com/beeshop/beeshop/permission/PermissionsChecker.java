package com.beeshop.beeshop.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Author : cooper
 * Time :  2017/12/28 上午11:24
 * Description : 权限检查
 */
public class PermissionsChecker {

    private Context mContext;

    public PermissionsChecker(Context context) {
        this.mContext = context;
    }

    private boolean lacksPermission(String string) {
        return ContextCompat.checkSelfPermission(this.mContext, string) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * 权限数组检查
     * @param strArr
     */
    public boolean lacksPermissions(String... strArr) {
        for (String lacksPermission : strArr) {
            if (lacksPermission(lacksPermission)) {
                return true;
            }
        }
        return false;
    }



}
