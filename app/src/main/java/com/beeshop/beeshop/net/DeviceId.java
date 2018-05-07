package com.beeshop.beeshop.net;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by apolunor on 2017/7/25.
 */

public final class DeviceId {

    private static final String TAG = "DeviceId";
    private static final boolean DEBUG = false;

    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    private static final String DEFAULT_ANDROID_ID = "9774d56d682e549c";

    private static final String DELIMITER = "_";

    private static final String FILE_INSIDE_DEVICEID = "deviceid";
    private static final String FILE_INSIDE_IMEI = "imei";
    private static final String DIR_SDCARD = "/guazi/data/.a0f89";
    private static final String FILE_SDCARD_DEVICEID = "a16429c1e2";
    private static final String FILE_SDCARD_IMEI = "a3a2fc2bf8f33";

    private static String sImei;
    private static String sDeviceId;

    private DeviceId() {
    }

    public static String get(final Context context) {
        final String imei = getImei(context);
        return TextUtils.isEmpty(imei) ? getDeviceId(context) : imei;
    }

    synchronized static String getImei(final Context context) {
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        final File insideFile = new File(context.getFilesDir(), FILE_INSIDE_IMEI);
        try {
            if (insideFile.exists() && insideFile.isFile()) {
                sImei = read(insideFile);
                if (!TextUtils.isEmpty(sImei)) {
                    return sImei;
                }
            }
        } catch (Exception e) {
        }
        boolean canUseStorage = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || !Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState())) {
            canUseStorage = false;
        }
        final File root = Environment.getExternalStorageDirectory();
        final File sdcradDir = new File(root, DIR_SDCARD);
        final File sdcradFile = new File(sdcradDir, FILE_SDCARD_IMEI);
        try {
            if (canUseStorage && sdcradFile.exists() && sdcradFile.isFile()) {
                sImei = read(sdcradFile);
                if (!TextUtils.isEmpty(sImei)) {
                    save(insideFile, sImei);
                    return sImei;
                }
            }
        } catch (Exception e) {
        }
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                return sImei;
            }
            sImei = ((TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE)).getDeviceId();
            if (!TextUtils.isEmpty(sImei)) {
                save(insideFile, sImei);
                if (canUseStorage) {
                    boolean sdcradPathExists;
                    if (!sdcradDir.exists()) {
                        sdcradPathExists = sdcradDir.mkdirs();
                    } else {
                        sdcradPathExists = true;
                    }
                    if (sdcradPathExists) {
                        save(sdcradFile, sImei);
                    }
                }
                return sImei;
            }
        } catch (Exception e) {
            // ignore
        }
        return sImei;
    }

    private static String generateDeviceId(Context context) {
        final String wlanMac = getWlanMac(context);
        final String androidId = getAndroidId(context);
        final String buildInfo = getBuildInfo();

        final StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(wlanMac) && !TextUtils.equals(wlanMac, DEFAULT_MAC_ADDRESS)) {
            sb.append(wlanMac);
            sb.append(DELIMITER);
        }
        if (!TextUtils.isEmpty(androidId) && !TextUtils.equals(wlanMac, DEFAULT_ANDROID_ID)) {
            sb.append(androidId);
            sb.append(DELIMITER);
        }
        sb.append(buildInfo);

        final SecureRandom secureRandom = new SecureRandom();
        if (secureRandom != null) {
            final byte[] randomBytes = new byte[8];
            secureRandom.nextBytes(randomBytes);
            sb.append(DELIMITER);
            sb.append(new String(randomBytes));
        }

        if (DEBUG) {
            Log.d(TAG, sb.toString());
        }

        final String uuid = UUID.nameUUIDFromBytes(sb.toString().getBytes()).toString();
        return uuid.replaceAll("\\-", "");
    }

    synchronized static String getDeviceId(Context context) {
        if (!TextUtils.isEmpty(sDeviceId) && !TextUtils.equals(sDeviceId, "unknown")) {
            return sDeviceId;
        }
        final File insideFile = new File(context.getFilesDir(), FILE_INSIDE_DEVICEID);
        try {
            if (insideFile.exists() && insideFile.isFile()) {
                sDeviceId = read(insideFile);
                if (!TextUtils.isEmpty(sDeviceId)) {
                    return sDeviceId;
                } else {
                    insideFile.delete();
                }
            }
        } catch (Exception e) {
        }

        boolean canUseStorage = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || !Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState())) {
            canUseStorage = false;
        }
        final File root = Environment.getExternalStorageDirectory();
        final File sdcradDir = new File(root, DIR_SDCARD);
        final File sdcradFile = new File(sdcradDir, FILE_SDCARD_DEVICEID);
        try {
            if (canUseStorage && sdcradFile.exists() && sdcradFile.isFile()) {
                sDeviceId = read(sdcradFile);
                if (!TextUtils.isEmpty(sDeviceId)) {
                    save(insideFile, sDeviceId);
                    return sDeviceId;
                }
            }
        } catch (Exception e) {
        }
        try {
            sDeviceId = generateDeviceId(context);
            if (!TextUtils.isEmpty(sDeviceId)) {
                save(insideFile, sDeviceId);
                if (canUseStorage) {
                    boolean sdcradPathExists;
                    if (!sdcradDir.exists()) {
                        sdcradPathExists = sdcradDir.mkdirs();
                    } else {
                        sdcradPathExists = true;
                    }
                    if (sdcradPathExists) {
                        save(sdcradFile, sDeviceId);
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            if (TextUtils.isEmpty(sDeviceId)) {
                sDeviceId = "unknown";
            }
        }
        return sDeviceId;
    }

    static String getWlanMac(Context context) {
        final WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wifiInfo = wm == null ? null : wm.getConnectionInfo();
        return wifiInfo == null ? DEFAULT_MAC_ADDRESS : wifiInfo.getMacAddress();
    }

    public static String getAndroidId(Context context) {
        final ContentResolver contentResolver = context.getContentResolver();
        return contentResolver == null ? DEFAULT_ANDROID_ID
                : Secure.getString(contentResolver, Secure.ANDROID_ID);
    }

    public static String getBuildInfo() {
        final String brand = Build.BRAND;
        final String product = Build.PRODUCT;
        final String board = Build.BOARD;
        final String cpu_abi = Build.CPU_ABI;
        final String device = Build.DEVICE;
        final String hardware = Build.HARDWARE;
        final String serial = Build.SERIAL;
        final String host = Build.HOST;
        final String id = Build.ID;
        final String model = Build.MODEL;

        StringBuilder sb = new StringBuilder();
        sb.append(brand);
        sb.append(DELIMITER);
        sb.append(product);
        sb.append(DELIMITER);
        sb.append(board);
        sb.append(DELIMITER);
        sb.append(cpu_abi);
        sb.append(DELIMITER);
        sb.append(device);
        sb.append(DELIMITER);
        sb.append(serial);
        sb.append(DELIMITER);
        sb.append(hardware);
        sb.append(DELIMITER);
        sb.append(host);
        sb.append(DELIMITER);
        sb.append(id);
        sb.append(DELIMITER);
        sb.append(model);
        sb.append(DELIMITER);

        return sb.toString();
    }

    private static String read(File location) {
        RandomAccessFile f = null;
        byte[] bytes = null;
        try {
            f = new RandomAccessFile(location, "r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
        } catch (Exception e) {
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                }
            }
        }
        return bytes == null ? null : new String(bytes);
    }

    private static void save(File location, String id) {
        FileOutputStream out = null;
        try {
            if (location.createNewFile()) {
                out = new FileOutputStream(location);
                out.write(id.getBytes());
            }
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
