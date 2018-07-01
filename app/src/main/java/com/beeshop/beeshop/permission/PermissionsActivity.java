package com.beeshop.beeshop.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.views.DialogFactory;


public class PermissionsActivity extends Activity {
    private static final String EXTRA_PERMISSIONS = "jason_permission";
    private static final String PACKAGE_URL_SCHEME = "package:";
    public static final int PERMISSIONS_DENIED = 1;

    private boolean isRequireCheck;
    private PermissionsChecker mChecker;


    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permissions);
        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }



    private void allPermissionsGranted() {
        setResult(0);
        finish();
    }

    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i += PERMISSIONS_DENIED) {
            if (iArr[i] == -1) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions(String... strArr) {
        ActivityCompat.requestPermissions(this, strArr, 0);
    }

    private void showMissingPermissionDialog() {

        View view = View.inflate(this, R.layout.dialog_permission, null);
        final Dialog dialog = DialogFactory.createRoundDialog(this, view, false);
        TextView exit = (TextView) view.findViewById(R.id.exit);
        TextView setting = (TextView) view.findViewById(R.id.setting);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsActivity.this.setResult(PermissionsActivity.PERMISSIONS_DENIED);
                PermissionsActivity.this.finish();
                dialog.dismiss();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsActivity.this.startAppSettings();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    public static void startActivityForResult(Activity activity, int i, String... strArr) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, strArr);
        ActivityCompat.startActivityForResult(activity, intent, i, null);
    }

    private void startAppSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse(new StringBuilder(PACKAGE_URL_SCHEME).append(getPackageName()).toString()));
        startActivity(intent);
    }


    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 0 && hasAllPermissionsGranted(iArr)) {
            isRequireCheck = true;
            allPermissionsGranted();
            return;
        }
        this.isRequireCheck = false;
        showMissingPermissionDialog();
    }

    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            for (int i = 0; i < permissions.length; i++) {
                LogUtil.e("permissions[]"+ "string[" + i + "]......." + permissions[i]);
            }
            if (mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions);
            } else {
                allPermissionsGranted();
            }
        }
        this.isRequireCheck = true;
    }
}